package team.themoment.gsmNetworking.domain.mentor.dto

import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class MentorCareerDto(
    @field:NotBlank
    val companyName: String,

    val companyUrl: String?,

    @field:NotBlank
    val position: String,

    @field:NotBlank
    val startDate: LocalDate,

    val endDate: LocalDate?,

    val isWorking: Boolean?
)
