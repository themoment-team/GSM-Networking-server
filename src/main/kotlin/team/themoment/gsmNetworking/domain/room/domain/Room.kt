package team.themoment.gsmNetworking.domain.room.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.CompoundIndexes
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "room")
@CompoundIndexes(
    CompoundIndex(
        def = "{'userInfos.userId': 1, 'userInfos.lastViewedChatId': -1}",
        name = "room_user_info_compound_index",
        unique = true
    )
)
class Room(
    @Id
    val id: UUID,
    val name: String,
    val userInfos: List<ChatUserInfo>
)
