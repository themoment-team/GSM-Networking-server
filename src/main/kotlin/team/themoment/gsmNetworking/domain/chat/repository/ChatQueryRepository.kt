package team.themoment.gsmNetworking.domain.chat.repository

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.dto.domain.RecentChatQueryDto
import team.themoment.gsmNetworking.domain.chat.enums.Direction
import java.time.LocalDateTime

interface ChatQueryRepository {
    fun findChatsByTimeAndDirection(roomId: Long, direction: Direction, time: LocalDateTime, limit: Long): List<BaseChat>
    fun findRecentChats(roomId: Long, limit: Long): List<BaseChat>
    fun findRoomsRecentChats(roomInfos: List<RecentChatQueryDto>, limit: Long): List<BaseChatDto>
}
