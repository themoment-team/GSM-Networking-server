package team.themoment.gsmNetworking.domain.chat.dto.ws.response

import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import java.util.UUID

data class ChatResponse(
    val charId: UUID,
    val roomId: Long,
    val content: String,
    val senderId: Long,
    val type: ChatType
)
