package team.themoment.gsmNetworking.domain.message.dto.api.req

data class CheckMessageReq(
    val to: Long,
    val epochMilli: Long
)
