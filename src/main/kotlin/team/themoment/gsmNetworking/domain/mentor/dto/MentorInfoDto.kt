package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MentorInfoDto(
    val id: Long,
    val name: String,
    val email: String,
    val generation: Int,
    val position: String,
    val company: CompanyInfo,
    @field:JsonProperty("SNS")
    val sns: String?,
    val profileUrl: String?
) {

    data class CompanyInfo(
        val name: String,
        @field:JsonProperty("URL")
        val url: String
    )

}