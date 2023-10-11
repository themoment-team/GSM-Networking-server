package team.themoment.gsmNetworking.domain.chat.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import team.themoment.gsmNetworking.domain.room.domain.ChatRoom

@Document(collection = "chat")
@TypeAlias("user")
class UserChat(
    id: ObjectId,
    room: ChatRoom,
    val senderId: Long,
    content: String,
) : BaseChat(id, room, content) {
}
