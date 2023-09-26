package team.themoment.gsmNetworking.domain.mentor.dto

import java.time.LocalDate
import javax.validation.constraints.NotBlank

data class MentorCareerDto(
    @field:NotBlank
    val companyName: String,

    @field:NotBlank
    val companyUrl: String,

    @field:NotBlank
    val position: String,

    @field:NotBlank
    val startDate: LocalDate,

    @field:NotBlank
    val endDate: LocalDate,

    val isWorking: Boolean?
)