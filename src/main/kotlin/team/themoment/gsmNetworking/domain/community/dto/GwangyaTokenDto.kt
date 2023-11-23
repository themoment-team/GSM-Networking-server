package team.themoment.gsmNetworking.domain.community.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class GwangyaTokenDto(
    val gwangyaToken: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expiredTime: LocalDateTime
)
