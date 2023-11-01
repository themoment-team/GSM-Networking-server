package team.themoment.gsmNetworking.domain.chat.dto.ws.request


data class QueryRecentRoomsRequest(
    val userId: Long,
    val limit: Int
)
