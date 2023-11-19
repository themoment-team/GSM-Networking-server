package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileMentorInfoDto(
    val id: Long,
    val name: String,
    val email: String,
    val generation: Int,
    @field:JsonProperty("SNS")
    val sns: String?,
    val profileUrl: String?,
    val registered: Boolean,
    val career: List<ProfileCareerInfoDto>
)
