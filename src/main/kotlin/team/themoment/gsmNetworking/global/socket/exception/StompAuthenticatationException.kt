package team.themoment.gsmNetworking.global.socket.exception

import team.themoment.gsmNetworking.common.exception.model.ErrorCode

class StompAuthenticatationException(
    val code: ErrorCode = ErrorCode.DEFAULT,
    override val message: String
) : RuntimeException(message) {
}

