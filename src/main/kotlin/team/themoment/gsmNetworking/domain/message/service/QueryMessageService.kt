package team.themoment.gsmNetworking.domain.message.service

import team.themoment.gsmNetworking.domain.message.dto.domain.HeaderDto
import team.themoment.gsmNetworking.domain.message.dto.api.res.HeaderRes
import team.themoment.gsmNetworking.domain.message.dto.api.res.MessageRes
import team.themoment.gsmNetworking.domain.message.enums.QueryDirection
import java.time.Instant

interface QueryMessageService {

    fun getHeaderByUserIds(user1Id: Long, user2Id: Long): HeaderDto?

    fun getMessageInfosByUserId(userId: Long, time: Instant, limit: Long): List<HeaderRes>

    fun getMessagesBetweenUsers(
        user1Id: Long,
        user2Id: Long,
        time: Instant,
        limit: Long,
        direction: QueryDirection
    ): List<MessageRes>

}
