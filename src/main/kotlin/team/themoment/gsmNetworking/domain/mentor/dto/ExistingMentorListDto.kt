package team.themoment.gsmNetworking.domain.mentor.dto

data class ExistingMentorListDto (
    val existingMentors: List<ExistingMentorDto>
)

data class ExistingMentorDto(
    val name: String? = "",
    val email: String? = "",
    val generation: Long? = 0,
    val position: String? = "",
    val company: MentorCompanyDto? = MentorCompanyDto(),
    val SNS: String? = ""
)

data class MentorCompanyDto(
    val name: String? = "",
    val URL: String? = ""
)