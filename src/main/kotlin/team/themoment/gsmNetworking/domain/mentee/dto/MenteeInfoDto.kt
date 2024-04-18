package team.themoment.gsmNetworking.domain.mentee.dto

data class MenteeInfoDto (
    val id: Long,

    val name: String,

    val email: String,

    val phoneNumber: String,

    val generation: Int,

    val defaultImgNumber: Int,

    val profileUrl: String?
)
