package team.themoment.gsmNetworking.domain.message.dto.api.res

import team.themoment.gsmNetworking.domain.message.domain.Message
import java.util.UUID

data class CheckMessageRes(
    val messageId: UUID
) {
    val isChecked: Boolean = true
}
