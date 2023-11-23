package team.themoment.gsmNetworking.domain.community.dto

import java.time.LocalDateTime

data class GwangyaTokenDto(
    val gwangyaToken: String,
    val expiredTime: LocalDateTime
)
