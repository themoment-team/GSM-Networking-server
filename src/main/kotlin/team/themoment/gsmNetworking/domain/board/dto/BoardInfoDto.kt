package team.themoment.gsmNetworking.domain.board.dto

import com.fasterxml.jackson.annotation.JsonFormat
import team.themoment.gsmNetworking.domain.board.domain.Category
import java.time.LocalDateTime

data class BoardInfoDto (
    val id: Long,
    val title: String,
    val category: Category,
    val authorName: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime
)
