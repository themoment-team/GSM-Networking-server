package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TempMentorInfoDto(
    var id: Long,
    val firebaseId: String,
    val name: String,
    val email: String,
    val generation: Int,
    val position: String,
    val company: CompanyInfoDto,
    @field:JsonProperty("SNS")
    val snsUrl: String?
) {
    fun toMentorInfoDto() = MentorInfoDto(
        id = id,
        name = name,
        email = email,
        generation = generation,
        position = position,
        company = company,
        sns = snsUrl,
        profileUrl = null,
        registered = false
    )
}
