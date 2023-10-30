package team.themoment.gsmNetworking.domain.chat.dto.ws.request

data class UserChatRequest(
    val roomId: Long,
    val content: String
)
