package team.themoment.gsmNetworking.domain.message.dto.api.req

import java.util.UUID

data class CheckMessageReq(
    val to: Long,
    val messageId: UUID
)
