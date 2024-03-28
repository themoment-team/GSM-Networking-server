package team.themoment.gsmNetworking.domain.board.dto

import com.fasterxml.jackson.annotation.JsonFormat
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.comment.dto.Author
import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto
import java.time.LocalDateTime

data class BoardInfoDto (
    val id: Long,
    val title: String,
    val content: String,
    val boardCategory: BoardCategory,
    val author: Author,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime,
    val comments: List<CommentListDto>
)
