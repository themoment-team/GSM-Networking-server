package team.themoment.gsmNetworking.domain.chat.dto.ws.response

import team.themoment.gsmNetworking.domain.chat.domain.BaseChat
import team.themoment.gsmNetworking.domain.room.domain.RoomUser

// TODO domain 대신 dto 사용하기, 이름 더 적절하게 변경
data class UserChatServiceRs(
    val savedChat: BaseChat,
    val updatedRoomUsers: List<RoomUser>
) {
}
