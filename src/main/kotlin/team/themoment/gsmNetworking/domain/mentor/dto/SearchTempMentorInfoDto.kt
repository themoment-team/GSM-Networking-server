package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SearchTempMentorInfoDto(
    val id: Long,
    val firebaseId: String,
    val name: String,
    val email: String?,
    val generation: Int,
    val position: String,
    val company: CompanyInfoDto,
    @field:JsonProperty("SNS")
    val snsUrl: String?
)
