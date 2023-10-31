package team.themoment.gsmNetworking.domain.chat.dto.ws.request


data class QueryRecentChatRequest(
    val roomId: Long,
    val limit: Long
)
