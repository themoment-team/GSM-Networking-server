package team.themoment.gsmNetworking.common.exception

import team.themoment.gsmNetworking.common.exception.model.ErrorCode

/**
 * STOMP 통신 중인 사용자에게 예외 상황을 알리기 위해 사용합니다.
 * 사용하는 도메인에 맞게 구현하여 사용합니다.
 */
abstract class StompException(
    val code: ErrorCode = ErrorCode.DEFAULT,
    override val message: String
) : RuntimeException(message)
