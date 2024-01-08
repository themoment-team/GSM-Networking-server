package team.themoment.gsmNetworking.domain.mentor.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MentorSaveInfoDto(
    @field:NotBlank
    val name: String,

    @field:NotNull
    val generation: Int,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val email: String,

    val snsUrl: String?,

    @field:NotEmpty
    val career: List<MentorCareerDto>
)
