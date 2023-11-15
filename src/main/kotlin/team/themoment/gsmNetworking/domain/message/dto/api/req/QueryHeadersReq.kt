package team.themoment.gsmNetworking.domain.message.dto.api.req

data class QueryHeadersReq(
    val userId: Long,
    val epochMilli: Long,
    val limit: Long
)
