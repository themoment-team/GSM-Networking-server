package team.themoment.gsmNetworking.domain.like.service

import team.themoment.gsmNetworking.domain.like.dto.LikeStatusDto

interface SaveLikeUseCase {
    fun like(boardId: Long): LikeStatusDto
}