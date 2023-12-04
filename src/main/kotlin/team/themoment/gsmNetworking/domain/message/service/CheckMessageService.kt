package team.themoment.gsmNetworking.domain.message.service

import java.time.Instant

interface CheckMessageService {
    fun execute(toUserId: Long, fromUserId: Long, time: Instant)
}
