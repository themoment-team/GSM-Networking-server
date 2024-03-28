package team.themoment.gsmNetworking.domain.comment.dto

data class CommentInfoDto (
    val commentId: Long,
    val comment: String,
    val author: AuthorDto
)
