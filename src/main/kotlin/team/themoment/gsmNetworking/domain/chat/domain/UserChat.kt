package team.themoment.gsmNetworking.domain.chat.domain

import org.springframework.data.annotation.TypeAlias
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import javax.persistence.Entity

@Entity
@TypeAlias(ChatType.Alias.USER)
class UserChat(
    id: Long,
    room: Room,
    senderId: Long,
    content: String
) : BaseChat(
    id = id,
    room = room,
    content = content,
    senderId = senderId,
    type = ChatType.USER
) {
}
