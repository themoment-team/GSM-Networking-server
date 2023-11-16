package team.themoment.gsmNetworking.domain.message.dto.api.code

import team.themoment.gsmNetworking.common.socket.message.MessageCode

enum class MessageMessageCode : MessageCode {
    MESSAGE,
    MESSAGES,
    HEADERS,
    MESSAGE_OCCUR,
    MESSAGE_CHECKED;

    override fun code(): String {
        return name
    }
}
