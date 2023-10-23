package team.themoment.gsmNetworking.domain.mentor.dto

data class ExistingMentorDto(
    val sns: String?,
    val companyName: String,
    val companyUrl: String,
    val email: String?,
    val generation: Long?,
    val name: String?,
    val position: String?
)
