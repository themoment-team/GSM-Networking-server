package team.themoment.gsmNetworking.domain.chat.service

import team.themoment.gsmNetworking.domain.chat.dto.domain.BaseChatDto
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.QueryRecentChatRequest

/**
 * 채팅 목록을 가져옵니다.
 */
interface QueryChatService {
    fun chatsByTimeAndDirection(req : QueryChatRequest) : List<BaseChatDto>

    fun recentChats(req : QueryRecentChatRequest) : List<BaseChatDto>
}
