package team.themoment.gsmNetworking.domain.comment.dto

data class CommentListDto (
    val commentId: Long,
    val comment: String,
    val author: Author,
    val replies: List<Reply>?
)