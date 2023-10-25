package team.themoment.gsmNetworking.domain.chat.domain

import team.themoment.gsmNetworking.common.util.UUIDUtils
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import java.time.Instant
import java.util.*
import javax.persistence.*


/**
 * 채팅을 저장하는 Entity의 추상클래스입니다.
 */
@Entity
@Table(name = "chat", indexes = [
    Index(name = "chat_idx_1", columnList = "room_id, chat_id DESC"),
])
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_num", discriminatorType = DiscriminatorType.INTEGER)
abstract class BaseChat(
    @Id
    @Column(name = "chat_id", columnDefinition = "BINARY(16)")
    val id: UUID,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    open val room: Room,

    @Column(name = "content", nullable = false)
    open val content: String,

    // SystemChat 같이 sender가 없는 경우 Long.MAX_VALUE를 사용함
    @Column(name = "sender_id", nullable = false)
    val senderId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type", nullable = false)
    open val type: ChatType,
) {
    val createAt: Instant
        get() = UUIDUtils.getInstant(id)
}
