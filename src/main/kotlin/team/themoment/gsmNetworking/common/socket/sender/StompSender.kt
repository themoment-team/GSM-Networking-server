package team.themoment.gsmNetworking.common.socket.sender

import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.socket.model.StompErrorResponse

/**
 * Stomp 메시지를 전송하는 역할의 인터페이스입니다.
 */
interface StompSender {
    // TODO 역할이 변경되었으므로 적절한 이름으로 변경하고, 주석 내용도 바꾸기
    //  기존의 STOMP 메시지를 전송하는 것에서
    //  사용자 상태에 따라 STOMP 메시지 전송 or 알람 등 다른 처리를 진행함
    //  즉, 외부에선 사용자 상태에 상관없이 해당 객체에 명령만 하면 됨

    /**
     * Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param path 메시지를 전송할 경로
     */
    fun sendMessage(message: StompMessage<*>, path: String)

    /**
     * 특정 User에게 Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param userId 메시지를 전송할 사용자의 식별자
     */
    fun sendMessageToUser(message: StompMessage<*>, userId: Long)

    /**
     * 특정 Session에게 Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param sessionId 메시지를 전송할 Client의 sessionId
     */
    fun sendMessageToSession(message: StompMessage<*>, sessionId: String)

    /**
     * Stomp 에러 메시지를 전송합니다.
     *
     * @param ex 전송할 에러 메시지
     */
    fun sendErrorMessage(ex: StompException) {
        val errorMessage = createErrorMessage(ex)
        sendMessage(errorMessage, ex.path)
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
