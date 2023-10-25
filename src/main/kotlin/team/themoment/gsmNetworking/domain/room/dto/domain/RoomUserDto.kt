package team.themoment.gsmNetworking.domain.room.dto.domain

import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import java.util.UUID

data class RoomUserDto(
    val id: Long,
    val userId: Long,
    val roomId: Long,
    val roomName: String,
    val lastViewedChatId: UUID,
    val maxChatInfo: MaxChatInfo
) {
    data class MaxChatInfo(
        val id: UUID,
        val content: String,
        val senderId: Long,
        val type: ChatType
    )
}
