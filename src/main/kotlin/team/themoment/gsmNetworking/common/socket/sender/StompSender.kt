package team.themoment.gsmNetworking.common.socket.sender

import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.socket.model.StompErrorResponse

/**
 * Stomp 메시지를 전송하는 역할의 인터페이스입니다.
 */
interface StompSender {

    /**
     * Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     */
    fun sendMessage(message: StompMessage<*>)

    /**
     * Stomp 에러 메시지를 전송합니다.
     *
     * @param ex 전송할 에러 메시지
     */
    fun sendErrorMessage(ex: StompException) {
        val errorMessage = createErrorMessage(ex)
        sendMessage(errorMessage)
    }

    /**
     * 지정한 [StompException] 클래스를 지원하는지 확인합니다.
     *
     * @param clazz 확인할 [StompException] 클래스
     * @return 지원하는 경우 True, 그렇지 않으면 False
     * @see StompException
     */
    fun supportsException(clazz: Class<out StompException>): Boolean

    /**
     * 해당 인터페이스를 구현하는 객체가 지원하는 [StompException]의 특정 구현체에 대한 에러 메시지를 생성합니다.
     *
     * @param ex 에러 메시지를 생성할 [StompException]의 구현체
     * @return 에러 메시지
     */
    fun createErrorMessage(ex: StompException): StompMessage<StompErrorResponse>

}
