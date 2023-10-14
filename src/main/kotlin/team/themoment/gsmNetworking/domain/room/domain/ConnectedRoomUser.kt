package team.themoment.gsmNetworking.domain.room.domain

import org.springframework.data.redis.core.RedisHash
import javax.persistence.*

@RedisHash(value = "connected_room_user")
data class ConnectedRoomUser(
    @Id
    val roomId: Long,

    val connectedUserIds: Set<Long>
) {
}
