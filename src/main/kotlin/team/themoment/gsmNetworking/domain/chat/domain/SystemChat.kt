package team.themoment.gsmNetworking.domain.chat.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import team.themoment.gsmNetworking.domain.room.domain.ChatRoom

@Document(collection = "chat")
@TypeAlias("system")
class SystemChat(
    id: ObjectId = ObjectId.get(),
    room: ChatRoom,
    content: TextContent
) : BaseChat(id, room, content, ChatType.SYSTEM) {
}
