package team.themoment.gsmNetworking.domain.room.dto.domain

import java.time.LocalDateTime

data class RoomUserDto(
    val id: Long,
    val userId: Long,
    val roomId: Long,
    val roomName: String,
    val lastViewedTime: LocalDateTime
)
