package team.themoment.gsmNetworking.common.socket.model

import team.themoment.gsmNetworking.common.exception.model.ErrorCode

/**
 * Exception 정보를 STOMP 메시지로 전달하기 위한 객체입니다.
 */
class StompErrorResponse(
    val errorCode: ErrorCode = ErrorCode.DEFAULT,
    val message: String
)