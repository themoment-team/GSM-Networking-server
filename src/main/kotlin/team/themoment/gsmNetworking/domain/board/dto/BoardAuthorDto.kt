package team.themoment.gsmNetworking.domain.board.dto

data class BoardAuthorDto (
    val id: Long,
    val name: String,
    val generation: Int,
    val profileUrl: String?,
    val defaultImgNumber: Int,
    val phoneNumber: String
)
