package team.themoment.gsmNetworking.domain.comment.service

import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto

interface QueryCommentInfoUseCase {
    fun queryCommentInfo(commentId: Long): CommentListDto
}