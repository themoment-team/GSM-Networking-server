package team.themoment.gsmNetworking.domain.chat.dto.domain

import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import java.util.*

data class BaseChatDto(
    val id: UUID,
    val room: RoomInfo,
    val content: String,
    val senderId: Long,
    val type: ChatType
) {
    data class RoomInfo(
        val id: Long
    )
}
