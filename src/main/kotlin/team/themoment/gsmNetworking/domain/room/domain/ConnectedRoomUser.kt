package team.themoment.gsmNetworking.domain.room.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * 특정 [Room]에 접속 중인 사용자 정보를 저장하는 RedisHash 클래스입니다.
 */
@RedisHash(value = "connected_room_user")
data class ConnectedRoomUser(
    @Id
    val roomId: Long,

    val connectedUserIds: Set<Long>
) {
}
