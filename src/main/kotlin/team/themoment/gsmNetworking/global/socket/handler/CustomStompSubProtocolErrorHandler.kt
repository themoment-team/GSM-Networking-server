package team.themoment.gsmNetworking.global.socket.handler

import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler
import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.global.socket.processor.StompErrorProcessor


/**
 * Socket 통신 전/후에 발생하는 에러를 처리하는 handler 입니다.
 */
@Component
class CustomStompSubProtocolErrorHandler(
    private val stompErrorProcessor: StompErrorProcessor
) : StompSubProtocolErrorHandler() {
    private val log = LoggerFactory.getLogger(this.javaClass)!! //TODO 로깅 구현 통일하기
    override fun handleClientMessageProcessingError(
        clientMessage: Message<ByteArray>?,
        ex: Throwable
    ): Message<ByteArray>? {
        when (val cause = ex.cause) {
            is StompException -> {
                stompErrorProcessor.sendErrors(cause)
            }

            is Exception -> {
                log.error("An error occurred", ex)
            }
        }

        return super.handleClientMessageProcessingError(clientMessage, ex)
    }
}