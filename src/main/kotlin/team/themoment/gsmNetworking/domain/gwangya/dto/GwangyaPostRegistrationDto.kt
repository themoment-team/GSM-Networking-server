package team.themoment.gsmNetworking.domain.gwangya.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class GwangyaPostRegistrationDto(
    @field:NotBlank
    @field:Size(max = 200)
    val content: String
)
