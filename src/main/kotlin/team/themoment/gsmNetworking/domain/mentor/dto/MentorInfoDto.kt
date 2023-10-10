package team.themoment.gsmNetworking.domain.mentor.dto

data class MentorInfoDto(
    val id: Long,
    val name: String,
    val email: String,
    val generation: Int,
    val position: String,
    val company: CompanyInfo,
    val sns: String?,
    val profileUrl: String?
) {

    data class CompanyInfo(
        val name: String,
        val url: String
    )

}