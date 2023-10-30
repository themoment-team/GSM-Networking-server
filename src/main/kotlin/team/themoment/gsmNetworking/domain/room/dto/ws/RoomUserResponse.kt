package team.themoment.gsmNetworking.domain.room.dto.ws

import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import java.util.*

data class RoomUserResponse(
    val roomUserId: Long,
    val userId: Long,
    val roomId: Long,
    val roomName: String,
    val lastViewedChatId: UUID,
    val recentChatInfo: RecentChatInfo
) {
    data class RecentChatInfo(
        val id: UUID,
        val content: String,
        val senderId: Long,
        val type: ChatType
    )
}
