package team.themoment.gsmNetworking.domain.user.dto

data class UserSimpleInfoDto (
    val id: Long,
    val name: String,
    val generation: Int,
    val profileUrl: String?
)
