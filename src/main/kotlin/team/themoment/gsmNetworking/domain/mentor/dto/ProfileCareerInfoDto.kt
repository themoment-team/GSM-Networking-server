package team.themoment.gsmNetworking.domain.mentor.dto

import java.time.LocalDate

data class ProfileCareerInfoDto(
    val id: Long,
    val position: String,
    val companyName: String,
    val companyUrl: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val isWorking: Boolean
)
