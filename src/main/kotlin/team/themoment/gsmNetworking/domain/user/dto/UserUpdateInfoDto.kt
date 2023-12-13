package team.themoment.gsmNetworking.domain.user.dto

data class UserUpdateInfoDto(
    val name: String,
    val generation: Int,
    val phoneNumber: String,
    val email: String,
    val snsUrl: String?,
    val profileUrl: String?
)
