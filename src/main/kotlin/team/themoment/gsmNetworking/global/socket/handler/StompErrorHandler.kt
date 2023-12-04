package team.themoment.gsmNetworking.global.socket.handler

import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler
import team.themoment.gsmNetworking.common.exception.model.ErrorCode
import team.themoment.gsmNetworking.global.socket.exception.StompAuthenticatationException


/**
 * Socket 통신 전/후에 발생하는 에러를 처리하는 handler 입니다.
 */
class StompErrorHandler : StompSubProtocolErrorHandler() {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun handleClientMessageProcessingError(clientMessage: Message<ByteArray>?, ex: Throwable): Message<ByteArray> {
        return when (val cause = ex.cause) {
            is StompAuthenticatationException ->
                handleStompAuthenticatationException(cause)
            else ->
                handleUnexpectedException(cause)
        }
    }

    private fun handleStompAuthenticatationException(cause: StompAuthenticatationException): Message<ByteArray> {
        log.warn("STOMP 통신 중 StompException 발생: [${cause.code}] ${cause.message}")
        val errorMessage = """
            {
                "code": "${cause.code.name}",
                "message": "${cause.message}"
            }
        """.trimIndent()
        return createErrorMessage(errorMessage)
    }

    private fun handleUnexpectedException(cause: Throwable?): Message<ByteArray> {
        log.error("예상하지 못한 예외 발생: ", cause)
        val errorMessage = """
            {
                "code": "${ErrorCode.INTERNAL_SERVER_ERROR}",
                "message": "예상하지 못한 예외 발생"
            }
        """.trimIndent()
        return createErrorMessage(errorMessage)
    }

    private fun createErrorMessage(errorMessage: String): Message<ByteArray> {
        val accessor = StompHeaderAccessor.create(StompCommand.ERROR)
        accessor.setLeaveMutable(true)
        return MessageBuilder.createMessage(
            errorMessage.toByteArray(Charsets.UTF_8),
            accessor.getMessageHeaders()
        )
    }
}
