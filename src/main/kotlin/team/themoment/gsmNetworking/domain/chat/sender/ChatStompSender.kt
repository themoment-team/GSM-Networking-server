package team.themoment.gsmNetworking.domain.chat.sender

import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.socket.model.StompErrorResponse
import team.themoment.gsmNetworking.common.socket.sender.StompSender
import team.themoment.gsmNetworking.common.util.StompPathUtil
import team.themoment.gsmNetworking.domain.chat.exception.ChatStompException
import team.themoment.gsmNetworking.domain.connect.ConnectedInfoRepository

@Component
class ChatStompSender(
    private val messageSendingOperations: SimpMessageSendingOperations,
    private val connectedInfoRepository: ConnectedInfoRepository
) : StompSender {

    override fun sendMessage(message: StompMessage<*>, path: String) {
        messageSendingOperations.convertAndSend(path, message)
    }

    override fun sendMessageToUser(message: StompMessage<*>, userId: Long) {
        // PREFIX_TO_USER를 가지는(user queue를 구독하는) 모든 session에 message 보내기
        val subUrls = getSubscriptionUrls(userId)
        if (subUrls.isNotEmpty()) {
            subUrls.forEach { path -> sendMessage(message, path) }
        } else {
            // TODO 알람 보내기, 아직 구현 계획은 없음
        }
    }

    override fun sendMessageToSession(message: StompMessage<*>, sessionId: String) {
        val path = "${StompPathUtil.PREFIX_TO_USER}/$sessionId"
//        if (isSessionSubscribedToPath(sessionId, path)) {
//            sendMessage(message, path)
//        }

        // isSessionSubscribedToPath를 쓰면 Apic으로 테스트가 불가능, Apic은 한 session이 한 sub만 가능함
        if (isSessionSubscribing(sessionId)) {
            sendMessage(message, path)
        }
        // 연결이 끊어진 경우는 응답할 필요가 없으므로 스킵
    }

    override fun sendErrorMessage(ex: StompException) {
        val error = ex as ChatStompException
        // ChatStompException 가 발생하는 경우는 클라이언트가 요청할 때만이므로 sessoin 있는지만 확인
        if (isSessionSubscribedToPath(error.sessionId, error.path)) {
            super.sendErrorMessage(ex)
        }
        // 연결이 끊어진 경우는 응답할 필요가 없으므로 스킵
    }

    override fun supportsException(clazz: Class<out StompException>): Boolean =
        ChatStompException::class.java.isAssignableFrom(clazz)

    override fun createErrorMessage(ex: StompException): StompMessage<StompErrorResponse> {
        val error = ex as ChatStompException
        return StompMessage(StompErrorResponse(error.code, error.message), StompMessage.MessageType.ERROR)
    }

    private fun getSubscriptionUrls(userId: Long): List<String> {
        return connectedInfoRepository.findByUserId(userId)
            .flatMap { connect -> connect.subscribes.filter { it.subscribeUrl.contains(StompPathUtil.PREFIX_TO_USER) } }
            .map { it.subscribeUrl }
    }

    private fun isSessionSubscribedToPath(sessionId: String, path: String): Boolean {
        val connectedInfo = connectedInfoRepository.findById(sessionId).orElse(null)
        return connectedInfo?.subscribes?.any { it.subscribeUrl == path } ?: false
    }

    private fun isSessionSubscribing(sessionId: String): Boolean {
        val connectedInfo = connectedInfoRepository.findById(sessionId).orElse(null)
        return connectedInfo != null
    }

}
