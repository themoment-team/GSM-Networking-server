package team.themoment.gsmNetworking.domain.gwangya.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class GwangyaPostsRegistrationDto(
    @field:NotNull
    @field:Size(max = 200)
    val content: String
)
