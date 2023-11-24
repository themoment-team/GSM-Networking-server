package team.themoment.gsmNetworking.domain.community.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class GwangyaPostsDto(
    @field:NotNull
    @field:Size(max = 200)
    val content: String
)
