package team.themoment.gsmNetworking.domain.user.dto


data class UserInfoDto(
    val id: Long,

    val name: String,

    val generation: Int,

    val email: String,

    val phoneNumber: String,

    val snsUrl: String?,

    val profileUrl: String?
)
