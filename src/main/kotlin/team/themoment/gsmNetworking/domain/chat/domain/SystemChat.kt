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
) : BaseChat(id, room, content, Long.MAX_VALUE, ChatType.SYSTEM) {
}
