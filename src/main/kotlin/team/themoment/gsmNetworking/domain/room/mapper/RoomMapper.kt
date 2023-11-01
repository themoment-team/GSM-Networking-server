package team.themoment.gsmNetworking.domain.chat.mapper

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.room.domain.RoomUser
import team.themoment.gsmNetworking.domain.room.dto.domain.RoomUserDto
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

        fun roomUserDtoToRoomUserResponse(dto: RoomUserDto): RoomUserResponse {
            return RoomUserResponse(
                roomUserId = dto.id,
                userId = dto.userId,
                roomId = dto.roomId,
                roomName = dto.roomName,
                lastViewedChatId = dto.lastViewedChatId,
                recentChatInfo = RoomUserResponse.RecentChatInfo(
                    id = dto.maxChatInfo.id,
                    content = dto.maxChatInfo.content,
                    senderId = dto.maxChatInfo.senderId,
                    type = dto.maxChatInfo.type
                )
            )
        }

        fun roomUserDtosToListRoomUserResponses(dtos: List<RoomUserDto>): List<RoomUserResponse> =
            dtos.map { roomUserDtoToRoomUserResponse(it) }
    }
}
