package team.themoment.gsmNetworking.domain.room.domain

import java.time.LocalDateTime
import javax.persistence.*

/**
 * 채팅 방에서의 사용자 정보를 저장하는 엔티티 입니다.
 */
@Entity
@Table(
    name = "room_user", indexes = [
        Index(
            name = "room_user_idx_1",
            columnList = "user_id, room_id, last_viewed_time DESC"
        )
    ]
)
class RoomUser(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_user_id")
    val id: Long = 0,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    val room: Room,

    @Column(name = "room_name", nullable = false)
    val roomName: String,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "last_viewed_time", nullable = false)
    val lastViewedTime: LocalDateTime

    //TODO 나중에 그룹채팅 기능 추가되면 방 권한도 추가
) {
}
