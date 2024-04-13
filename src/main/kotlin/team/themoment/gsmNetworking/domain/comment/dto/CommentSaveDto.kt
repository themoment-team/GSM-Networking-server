package team.themoment.gsmNetworking.domain.comment.dto

import io.micrometer.core.lang.Nullable
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CommentSaveDto (
    @field:NotNull
    val boardId: Long,
    @field:Nullable
    val parentCommentId: Long?,
    @field:Nullable
    val replyCommentId: Long?,
    @field:NotBlank
    @Max(300)
    val comment: String
)
