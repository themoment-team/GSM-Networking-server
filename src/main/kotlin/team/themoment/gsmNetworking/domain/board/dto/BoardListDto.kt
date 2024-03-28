package team.themoment.gsmNetworking.domain.board.dto

import com.fasterxml.jackson.annotation.JsonFormat
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import java.time.LocalDateTime

data class BoardListDto (
    val id: Long,
    val title: String,
    val boardCategory: BoardCategory,
    val authorName: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime
)
