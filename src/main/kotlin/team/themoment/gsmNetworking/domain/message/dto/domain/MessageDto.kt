package team.themoment.gsmNetworking.domain.message.dto.domain

import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.*

data class MessageDto(
    val messageId: UUID,
    val user1Id: Long,
    val user2Id: Long,
    val direction: Message.MessageDirection,
    val content: String
)
