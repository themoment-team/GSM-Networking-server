package team.themoment.gsmNetworking.domain.message.dto.api.res

import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.UUID

data class MessageRes(
    val messageId: UUID,
    val user1Id: Long,
    val user2Id: Long,
    val direction: Message.MessageDirection,
    val content: String,
    val messageTime: Long,
    val isCheckedUser1: Boolean,
    val isCheckedUser2: Boolean,
)
