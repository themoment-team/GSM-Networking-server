package team.themoment.gsmNetworking.domain.mentor.dto

import java.time.LocalDate

data class MyCareerInfoDto(
    val id: Long,
    val position: String,
    val companyName: String,
    val companyUrl: String?,
    val lat: Double?,
    val lon: Double?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val isWorking: Boolean
)
