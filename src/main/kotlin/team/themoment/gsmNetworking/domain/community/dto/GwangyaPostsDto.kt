package team.themoment.gsmNetworking.domain.community.dto

import javax.validation.constraints.Size

data class GwangyaPostsDto(
    @Size(min = 0, max = 200)
    val content: String
)
