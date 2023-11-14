package team.themoment.gsmNetworking.domain.message.dto.domain

import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.UUID

data class MessageMetaDto(
    val user1Id: Long,
    val user2Id: Long,
    val messageDirection: Message.MessageDirection,
    val recentMessageId: UUID,
    val lastViewedChatId: UUID?
)
