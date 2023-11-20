package team.themoment.gsmNetworking.domain.message.dto.api.req

data class SaveMessageReq(
    val to: Long,
    val message: String
)
