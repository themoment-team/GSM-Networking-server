package team.themoment.gsmNetworking.domain.room.dto.domain

import java.util.*

data class FetchRoomDto(
    val roomId: Long,
    val roomUsers: List<RoomUserDto>,
) {
    data class RoomUserDto(
        val id: Long,
        val userId: Long,
        val roomName: String,
        val lastViewedChatId: UUID,
        val recentChatId: UUID
    )
}

