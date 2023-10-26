package team.themoment.gsmNetworking.domain.chat.domain

import com.fasterxml.uuid.Generators
import org.springframework.data.annotation.TypeAlias
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import java.util.UUID
import javax.persistence.Entity

/**
 * 사용자 채팅을 저장하는 Entity 클래스입니다.
 */
@Entity
@TypeAlias(ChatType.Alias.USER)
class UserChat(
    id: UUID = Generators.timeBasedEpochGenerator().generate(),
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
