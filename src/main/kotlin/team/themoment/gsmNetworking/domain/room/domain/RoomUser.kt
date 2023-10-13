package team.themoment.gsmNetworking.domain.room.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "room_user")
class RoomUser(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_user_id")
    val id: Long = 0,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    val room: Room,

    @Column(name = "room_name", unique = true)
    val roomName: String,

    // 결합도를 낮추기 위해 객체참조를 사용하지 않음 -- [우아한테크세미나] 우아한객체지향 1:04:00 ~ 1:18:00 참고
    @Column(name = "user_id", unique = true)
    val userId: Long,

    @Column(name = "last_viewed_time", unique = true)
    val lastViewedTime: LocalDateTime

    //TODO 나중에 그룹채팅 기능 추가되면 방 권한도 추가
) {
}
