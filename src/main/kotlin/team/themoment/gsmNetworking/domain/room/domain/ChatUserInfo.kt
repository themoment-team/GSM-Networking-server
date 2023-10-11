package team.themoment.gsmNetworking.domain.room.domain

import org.bson.types.ObjectId

data class ChatUserInfo(
    val userId: Long,
    val lastViewedChatId: ObjectId
)
