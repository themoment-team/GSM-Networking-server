package team.themoment.gsmNetworking.domain.comment.dto

data class ReplyCommentInfo (
    val commentId: Long,
    val comment: String,
    val author: AuthorDto,
    val replyCommentId: Long?
)
