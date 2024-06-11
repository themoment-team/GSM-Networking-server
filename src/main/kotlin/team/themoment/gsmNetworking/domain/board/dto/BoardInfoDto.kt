package team.themoment.gsmNetworking.domain.board.dto

import com.fasterxml.jackson.annotation.JsonFormat
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.comment.dto.AuthorDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto
import java.time.LocalDateTime

data class BoardInfoDto (
    val id: Long,
    val title: String,
    val content: String,
    val boardCategory: BoardCategory,
    val author: AuthorDto,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime,
    val comments: List<CommentListDto>,
    val likeCount: Int,
    val isLike: Boolean,
    val fileUrls: List<String>
)
