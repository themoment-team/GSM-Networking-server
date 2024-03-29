package team.themoment.gsmNetworking.domain.comment.service

import team.themoment.gsmNetworking.domain.comment.dto.CommentInfoDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentSaveDto

interface SaveCommentUseCase {
    fun saveComment(commentSaveDto: CommentSaveDto, authenticationId: Long): CommentInfoDto
}
