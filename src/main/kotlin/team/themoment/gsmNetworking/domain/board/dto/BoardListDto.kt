package team.themoment.gsmNetworking.domain.board.dto

import com.fasterxml.jackson.annotation.JsonFormat
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.comment.dto.AuthorDto
import java.time.LocalDateTime

data class BoardListDto(
    val id: Long,
    val title: String,
    val boardCategory: BoardCategory,
    val author: AuthorDto,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime,
    val commentCount: Int,
    val likeCount: Int,
    val isLike: Boolean,
    val isPinned: Boolean,
)
