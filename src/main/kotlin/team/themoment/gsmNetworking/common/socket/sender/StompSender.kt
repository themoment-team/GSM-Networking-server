package team.themoment.gsmNetworking.common.socket.sender

import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.socket.message.StompMessage
import team.themoment.gsmNetworking.common.socket.model.StompErrorResponse

/**
 * Stomp 메시지를 전송하는 역할의 인터페이스입니다.
 */
interface StompSender {

    /**
     * 주어진 경로로 Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param path 메시지를 전송할 경로
     */
    fun sendMessage(message: StompMessage<*>, path: String)

    /**
     * 특정 사용자에게 Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param userId 메시지를 전송할 사용자의 식별자
     */
    fun sendMessageToUser(message: StompMessage<*>, userId: Long)

    /**
     * 특정 세션에게 Stomp 메시지를 전송합니다.
     *
     * @param message 전송할 메시지
     * @param sessionId 메시지를 전송할 클라이언트의 세션 ID
     */
    fun sendMessageToSession(message: StompMessage<*>, sessionId: String)

    /**
     * 주어진 경로로 Stomp 에러 메시지를 전송합니다.
     *
     * @param ex 전송할 에러 메시지
     * @param path 에러 메시지를 전송할 경로
     */
    fun sendErrorMessage(ex: StompException, path: String) =
        sendMessage(createErrorMessage(ex), path)

    /**
     * 특정 사용자에게 Stomp 에러 메시지를 전송합니다.
     *
     * @param ex 전송할 에러 메시지
     * @param userId 에러 메시지를 전송할 사용자의 식별자
     */
    fun sendErrorMessageToUser(ex: StompException, userId: Long) =
        sendMessageToUser(createErrorMessage(ex), userId)

    /**
     * 특정 세션에게 Stomp 에러 메시지를 전송합니다.
     *
     * @param ex 전송할 에러 메시지
     * @param sessionId 에러 메시지를 전송할 클라이언트의 세션 ID
     */
    fun sendErrorMessageToSession(ex: StompException, sessionId: String) =
        sendMessageToSession(createErrorMessage(ex), sessionId)

    /**
     * [StompException]에 대한 에러 메시지를 생성합니다.
     *
     * @param ex 에러 메시지를 생성할 [StompException]
     * @return 에러 메시지
     */
    fun createErrorMessage(ex: StompException): StompMessage<StompErrorResponse> =
        StompMessage(StompErrorResponse(ex.code, ex.message))

}
