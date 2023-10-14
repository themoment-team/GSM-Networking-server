package team.themoment.gsmNetworking.domain.room.domain

import javax.persistence.*

/**
 * 채팅방 정보를 저장하는 Entity 클래스입니다.
 */
@Entity(name = "room")
class Room(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    val id: Long = 0,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    val roomUsers: List<RoomUser>
) {
}
