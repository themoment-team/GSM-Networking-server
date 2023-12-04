package team.themoment.gsmNetworking.global.socket.interceptor

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.exception.model.ErrorCode
import team.themoment.gsmNetworking.global.socket.connect.ConnectInfo
import team.themoment.gsmNetworking.global.socket.connect.ConnectedInfoRepository
import team.themoment.gsmNetworking.global.security.jwt.TokenParser
import team.themoment.gsmNetworking.global.security.principal.AuthenticationDetails
import team.themoment.gsmNetworking.global.socket.exception.StompAuthenticatationException
import java.security.Principal

/**
 * STOMP 요청을 가로채 인증/인가, 연결 상태 설정 등 적절한 처리를 합니다.
 */
@Component
class StompInterceptor(
    private val tokenParser: TokenParser,
    private val connectedInfoRepository: ConnectedInfoRepository, //TODO repo 대신 service 객체를 만들어서 사용하게 변경
) : ChannelInterceptor {

    val TOKEN_HEADER = "access_token"

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message)
        if (StompCommand.CONNECT == accessor.command) {
            handleConnect(accessor)
        } else if (StompCommand.DISCONNECT == accessor.command) {
            handleDisconnect(accessor)
        } else if (StompCommand.SUBSCRIBE == accessor.command) {
            handleSubscribe(accessor)
        } else if (StompCommand.UNSUBSCRIBE == accessor.command) {
            handleUnsubscribe(accessor)
        } else if (StompCommand.SEND == accessor.command) {
            handleSend(accessor)
        }
        return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
    }

    private fun handleSend(accessor: StompHeaderAccessor) {
        val sessionId = accessor.sessionId!!

        // 인증 정보 입력
        connectedInfoRepository.findById(sessionId).ifPresent {
            val userId = it.userId.toString()
            accessor.user = Principal { userId }
        }
    }

    private fun handleUnsubscribe(accessor: StompHeaderAccessor) {
        val sessionId = accessor.sessionId!!
        val subscriptionId = accessor.subscriptionId!!

        // 구독 상태 제거
        connectedInfoRepository.findById(sessionId).ifPresent {
            it.removeSubscribe(subscriptionId)
        }
    }

    private fun handleSubscribe(accessor: StompHeaderAccessor) {
        val sessionId = accessor.sessionId!!
        val subscriptionId = accessor.subscriptionId!!
        val subscribeUrl = accessor.destination!!

        // 구독 상태 추가
        connectedInfoRepository.findById(sessionId).ifPresent {
            connectedInfoRepository.save(it.addSubscribe(ConnectInfo.Subscribe(subscriptionId, subscribeUrl)))
        }
    }

    private fun handleDisconnect(accessor: StompHeaderAccessor) {
        val sessionId = accessor.sessionId!!

        // 연결 상태 종료하기
        connectedInfoRepository.deleteById(sessionId)
    }

    private fun handleConnect(accessor: StompHeaderAccessor) {
        if (!hasAccessToken(accessor)) {
            throw StompAuthenticatationException(ErrorCode.AUTHENTICATION, "${TOKEN_HEADER}가 존재하지 않습니다")
        }
        val token = extractAccessToken(accessor)
        val sessionId = accessor.sessionId!!

        try {
            // JWT 인증
            val user = tokenParser.authentication(token).principal as AuthenticationDetails
            val userId = user.username

            //TODO 나중에 인가 처리하려면 ROLE도 추가해야 함 - 현재로써는 JWT 인증만 되면 OK
            accessor.user = Principal { userId }

            // 연결 상태로 변경
            connectedInfoRepository.save(ConnectInfo(sessionId, userId.toLong()))
        } catch (ex: ExpectedException) {
            // TODO FE에서 분기 처리 가능하도록 Code 다르게 설정하기
            throw StompAuthenticatationException(ErrorCode.AUTHENTICATION, "인증에 실패하였습니다")
        } catch (ex: ExpiredJwtException) {
            throw StompAuthenticatationException(ErrorCode.AUTHENTICATION, "만료된 JWT입니다")
        } // TODO JWT 예외처리 로직 더 자세하게 구분해야 할 수 있음
        catch (ex: JwtException) {
            throw StompAuthenticatationException(ErrorCode.AUTHENTICATION, "유효하지 않은 JWT입니다")
        }
    }

    private fun hasAccessToken(accessor: StompHeaderAccessor): Boolean {
        return accessor.getNativeHeader(TOKEN_HEADER) != null
    }

    private fun extractAccessToken(accessor: StompHeaderAccessor): String {
        if (hasAccessToken(accessor)) {
            val rowTypeAccessToken = accessor.getNativeHeader(TOKEN_HEADER)!!
            val accessTokenList = rowTypeAccessToken as ArrayList<String>
            return accessTokenList[0]
        }
        else throw IllegalStateException("해당 상황은 발생하지 않아야 합니다. 미리 hasAccessToken을 선언한 이후에 사용하세요")
    }
}
