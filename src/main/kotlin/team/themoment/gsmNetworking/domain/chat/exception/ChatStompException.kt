package team.themoment.gsmNetworking.domain.chat.exception

import team.themoment.gsmNetworking.common.exception.StompException
import team.themoment.gsmNetworking.common.exception.model.ErrorCode

class ChatStompException(
    code: ErrorCode,
    val sessionId: String,
    path: String,
    message: String
) : StompException(
    code = code,
    path = path,
    message = message
) {
}
