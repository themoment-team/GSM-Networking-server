package team.themoment.gsmNetworking.domain.room.domain

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.*

@RedisHash(value = "connected_room_user")
class ConnectedRoomUser(
    @Id
    val roomId: Long,

    @Indexed
    val connectedUserIds: Set<Long>
) {
}
