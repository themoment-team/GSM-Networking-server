package team.themoment.gsmNetworking.domain.comment.dto

data class CommentSaveDto (
    val boardId: Long,
    val replyCommentId: Long,
    val comment: String
)