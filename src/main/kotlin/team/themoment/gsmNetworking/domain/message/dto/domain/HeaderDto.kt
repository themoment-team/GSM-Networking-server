package team.themoment.gsmNetworking.domain.message.dto.domain

import java.util.*


data class HeaderDto(
    val headerId: Long,
    val user1Id: Long,
    val user2Id: Long,
    val recentMessageId: UUID
)
