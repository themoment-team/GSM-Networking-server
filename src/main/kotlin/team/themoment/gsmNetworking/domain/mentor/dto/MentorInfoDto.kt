package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MentorInfoDto(
    var id: Long,
    val name: String,
    val email: String,
    val generation: Int,
    val position: String,
    val company: CompanyInfoDto,
    @field:JsonProperty("SNS")
    val sns: String?,
    val profileUrl: String?,
    val registered: Boolean
)
