package team.themoment.gsmNetworking.domain.message.service

import java.util.UUID

interface CheckMessageService {
    fun execute(toUserId: Long, fromUserId: Long, messageId: UUID)
}
