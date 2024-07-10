package team.themoment.gsmNetworking.domain.mentor.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CompanyInfoDto(
    val name: String,
    @field:JsonProperty("URL")
    val url: String?,
    val lat: Double?,
    val lon: Double?
)
