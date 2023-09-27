package team.themoment.gsmNetworking.global.socket.processor

import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.sender.StompSender

/**
 * STOMP 에러 처리를 적절한 Sender에게 위임합니다.
 * @see StompSender
 */
@Component
class StompErrorProcessor(
    private val senders: List<StompSender>
) {

    init {
        require(senders.isNotEmpty()) {
            "하나 이상의 ${StompSender::javaClass.name}를 등록해야 합니다"
        }
    }

    /**
     * 처리 가능한 여러 [StompSender]에게 STOMP 에러 처리를 위임합니다.
     * @param ex [StompException]의 구현체
     * @see StompSender
     */
    fun sendErrors(ex: StompException) {
        val supportedSenders = senders.filter { it.supportsException(ex.javaClass) }

        if (supportedSenders.isEmpty()) {
            throw IllegalStateException("${ex.javaClass}를 처리를 지원하는 ${StompSender::javaClass.name}가 존재하지 않습니다")
        }

        supportedSenders.forEach { sender ->
            sender.sendErrorMessage(ex)
        }
    }
}