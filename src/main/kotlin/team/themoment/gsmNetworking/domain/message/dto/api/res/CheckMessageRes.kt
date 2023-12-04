package team.themoment.gsmNetworking.domain.message.dto.api.res

data class CheckMessageRes(
    val userId: Long,
    val checkedEpochMilli: Long
)
