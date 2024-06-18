package team.themoment.gsmNetworking.domain.mentor.dto

data class MentorCompanyAddressListDto (
    val id: Long,
    val name: String,
    val generation: Int,
    val company: CompanyAddressDto
)
