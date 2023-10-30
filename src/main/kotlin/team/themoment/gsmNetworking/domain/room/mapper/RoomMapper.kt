package team.themoment.gsmNetworking.domain.chat.mapper

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.ws.RoomUserResponse

class RoomMapper {

    companion object {

        fun chatAndRoomUserToRoomUserResponse(recentChat: BaseChat, roomUser: RoomUser): RoomUserResponse {
            return RoomUserResponse(
                roomUserId = roomUser.id,
                userId = roomUser.userId,
                roomId = roomUser.room.id,
                roomName = roomUser.roomName,
                lastViewedChatId = roomUser.lastViewedChatId,
                recentChatInfo = RoomUserResponse.RecentChatInfo(
                    id = recentChat.id,
                    content = recentChat.content,
                    senderId = recentChat.senderId,
                    type = recentChat.type
                )
            )
        }
    }
}
