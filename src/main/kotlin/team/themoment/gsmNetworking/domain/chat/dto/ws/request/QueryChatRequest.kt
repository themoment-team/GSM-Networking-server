package team.themoment.gsmNetworking.domain.chat.dto.ws.request

import team.themoment.gsmNetworking.domain.chat.enums.Direction
import java.time.Instant

data class QueryChatRequest(
    val roomId: Long,
    val direction: Direction,
    val time: Long,
    val limit: Long
)
