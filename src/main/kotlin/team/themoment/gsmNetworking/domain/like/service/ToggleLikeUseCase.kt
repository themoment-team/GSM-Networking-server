package team.themoment.gsmNetworking.domain.like.service

import team.themoment.gsmNetworking.domain.like.dto.LikeStatusDto

interface ToggleLikeUseCase {
    fun likeToggle(boardId: Long, authenticationId: Long): LikeStatusDto
}
