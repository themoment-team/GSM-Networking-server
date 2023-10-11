package team.themoment.gsmNetworking.domain.chat.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.data.mongodb.core.mapping.Field
import team.themoment.gsmNetworking.domain.room.domain.ChatRoom
import javax.persistence.Inheritance
import javax.persistence.InheritanceType

@Document(collection = "chat")
@CompoundIndexes(
    CompoundIndex(
        def = "{'roomId': -1, 'id': -1}",
        name = "chat_roomId_id_compound_index",
        unique = true
    )
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class BaseChat(
    @Id
    open val id: ObjectId,
    @Field(name = "roomId")
    @DocumentReference(lazy = true)
    open val room: ChatRoom,
    open val content: Any,
    open val type: ChatType
) {
}
