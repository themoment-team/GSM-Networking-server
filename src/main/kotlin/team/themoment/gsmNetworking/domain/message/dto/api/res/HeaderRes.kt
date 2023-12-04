package team.themoment.gsmNetworking.domain.message.dto.api.res

import java.time.Instant
import java.util.*

data class HeaderRes(
    val recentMessageId: UUID,
    val opponentUserId: Long,
    val recentMessageTime: Instant,
    val isChecked: Boolean
)
