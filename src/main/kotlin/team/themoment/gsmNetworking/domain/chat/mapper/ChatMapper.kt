package team.themoment.gsmNetworking.domain.chat.mapper

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.chat.domain.UserChat
import team.themoment.gsmNetworking.domain.chat.dto.ws.request.UserChatRequest
import team.themoment.gsmNetworking.domain.chat.dto.ws.response.ChatResponse
import team.themoment.gsmNetworking.domain.room.domain.Room

class ChatMapper {

    companion object {
        fun userChatRequestToChat(senderId: Long, req: UserChatRequest, room: Room): UserChat {
            return UserChat(
                room = room,
                content = req.content,
                senderId = senderId
            )
        }

        fun chatToChatResponse(chat: BaseChat): ChatResponse {
            return ChatResponse(
                charId = chat.id,
                roomId = chat.room.id,
                content = chat.content,
                senderId = chat.senderId,
                type = chat.type
            )
        }
    }
}
