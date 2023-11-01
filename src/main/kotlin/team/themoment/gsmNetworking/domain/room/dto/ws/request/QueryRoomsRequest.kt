package team.themoment.gsmNetworking.domain.room.dto.ws.request

data class QueryRoomsRequest(
    val userId: Long,
    val time: Long,
    val limit: Int
)
