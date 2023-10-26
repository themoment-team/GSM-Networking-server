package team.themoment.gsmNetworking.domain.chat.domain

import com.fasterxml.uuid.Generators
import org.springframework.data.annotation.TypeAlias
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import java.util.UUID
import javax.persistence.Entity

/**
 * 시스템 채팅을 저장하는 Entity 클래스입니다.
 */
@Entity
@TypeAlias(ChatType.Alias.SYSTEM)
class SystemChat(
    id: UUID = Generators.timeBasedEpochGenerator().generate(),
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
