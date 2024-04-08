package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MyMentorInfoDto(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val generation: Int,
    @field:JsonProperty("SNS")
    val sns: String?,
    val profileUrl: String?,
    val registered: Boolean,
    val defaultImgNumber: Int,
    val career: List<MyCareerInfoDto>
)
