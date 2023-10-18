package team.themoment.gsmNetworking.domain.chat.dto.domain

import java.time.LocalDateTime

data class RecentChatQueryDto(
    val roomId: Long,
    val lastViewedTime: LocalDateTime
)
