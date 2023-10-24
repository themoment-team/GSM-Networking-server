package team.themoment.gsmNetworking.domain.mentor.dto

data class ExistingMentorListDto (
    val existingMentors: MutableList<ExistingMentorDto>
)

data class ExistingMentorDto(
    val name: String? = "",
    val generation: Long? = 0,
    val email: String? = "",
    val position: String? = "",
    val company: MentorCompanyDto? = MentorCompanyDto(),
    val SNS: String? = ""
)

data class MentorCompanyDto(
    val name: String? = "",
    val URL: String? = ""
)