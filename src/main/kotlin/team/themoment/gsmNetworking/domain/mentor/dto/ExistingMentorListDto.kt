package team.themoment.gsmNetworking.domain.mentor.dto

data class ExistingMentorListDto (
    val existingMentors: MutableList<ExistingMentorDto>
)

data class ExistingMentorDto(
    val name: String?,
    val generation: Long?,
    val email: String?,
    val position: String?,
    val companyName: String,
    val companyUrl: String,
    val sns: String?
)