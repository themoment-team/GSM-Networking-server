package team.themoment.gsmNetworking.domain.chat.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import team.themoment.gsmNetworking.domain.chat.enums.ChatType
import team.themoment.gsmNetworking.domain.room.domain.Room
import java.time.LocalDateTime
import javax.persistence.*


/**
 * 채팅을 저장하는 Entity의 추상클래스입니다.
 */
@Entity(name = "chat")
@EntityListeners(AuditingEntityListener::class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_num", discriminatorType = DiscriminatorType.INTEGER)
abstract class BaseChat(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    val id: Long = 0,

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    open val room: Room,

    @Column(name = "content", nullable = false)
    open val content: String,

    // SystemChat 같이 sender가 없는 경우 Long.MAX_VALUE를 사용함
    @Column(name = "sender_id", nullable = false)
    val senderId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type", nullable = false)
    open val type: ChatType
) {
    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false)
    var createAt: LocalDateTime = LocalDateTime.MIN
        protected set
}
