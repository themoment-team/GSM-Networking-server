package team.themoment.gsmNetworking.domain.comment.dto

data class AuthorDto (
    val id: Long,
    val name: String,
    val generation: Int,
    val profileUrl: String?,
    val defaultImgNumber: Int
)
