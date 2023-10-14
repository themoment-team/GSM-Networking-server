package team.themoment.gsmNetworking.domain.chat.domain

import org.springframework.data.annotation.TypeAlias
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import javax.persistence.Entity

@Entity
@TypeAlias(ChatType.Alias.SYSTEM)
class SystemChat(
    id: Long,
    room: Room,
    content: String,
) : BaseChat(
    id = id,
    room = room,
    content = content,
    senderId = Long.MAX_VALUE,
    type = ChatType.SYSTEM
) {
}
