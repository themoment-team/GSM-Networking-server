package team.themoment.gsmNetworking.domain.room.service

import team.themoment.gsmNetworking.domain.chat.dto.ws.response.UserChatServiceRs

interface CreateRoomService {
    fun execute(user1Id: Long, user2Id: Long) : UserChatServiceRs
}
