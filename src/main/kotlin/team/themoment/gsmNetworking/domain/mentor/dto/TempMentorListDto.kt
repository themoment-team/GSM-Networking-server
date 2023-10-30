package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TempMentorListDto (
    val tempMentors: List<TempMentorInfoDto>
)

data class TempMentorInfoDto(
    val id: Int,
    val name: String,
    val email: String?,
    val generation: Int,
    val position: String,
    val company: CompanyInfoDto,
    @field:JsonProperty("SNS")
    val snsUrl: String?
){

    data class CompanyInfoDto(
        val name: String,
        @field:JsonProperty("URL")
        val url: String?
    )

}
