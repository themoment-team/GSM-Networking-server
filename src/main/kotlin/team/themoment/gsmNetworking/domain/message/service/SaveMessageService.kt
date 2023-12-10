package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.domain.MessageDto

interface SaveMessageService {
    fun execute(toUserId: Long, fromUserId: Long, message: String): MessageDto
}
