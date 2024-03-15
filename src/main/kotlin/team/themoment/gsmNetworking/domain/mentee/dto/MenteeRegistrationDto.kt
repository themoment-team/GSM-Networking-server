package team.themoment.gsmNetworking.domain.mentee.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class MenteeRegistrationDto(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val email: String,

    @field:NotBlank
    val phoneNumber: String,

    @field:NotNull
    val generation: Int
)
