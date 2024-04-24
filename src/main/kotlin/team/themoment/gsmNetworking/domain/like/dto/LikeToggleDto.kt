package team.themoment.gsmNetworking.domain.like.dto

import javax.validation.constraints.NotNull

data class LikeToggleDto (
    @NotNull
    val boardId: Long
)