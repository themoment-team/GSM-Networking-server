package team.themoment.gsmNetworking.domain.mentor.dto

data class MentorInfoDto(
    private val id: Long,
    private val name: String,
    private val email: String,
    private val generation: Int,
    private val position: String,
    private val company: CompanyInfo,
    private val sns: String
) {

    data class CompanyInfo(
        private val name: String,
        private val url: String
    )

}