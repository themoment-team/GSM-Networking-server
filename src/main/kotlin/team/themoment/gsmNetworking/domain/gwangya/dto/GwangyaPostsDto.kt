package team.themoment.gsmNetworking.domain.gwangya.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class GwangyaPostsDto(
    val id: Long,
    val content: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime
)
