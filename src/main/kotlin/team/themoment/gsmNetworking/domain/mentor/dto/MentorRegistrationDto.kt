package team.themoment.gsmNetworking.domain.mentor.dto

import javax.validation.constraints.NotBlank

data class MentorRegistrationDto(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val generation: Int,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotBlank
    val email: String,

    val snsUrl: String?,

    val profileUrl: String?,

    @field:NotBlank
    val career: List<MentorCareerDto>
)