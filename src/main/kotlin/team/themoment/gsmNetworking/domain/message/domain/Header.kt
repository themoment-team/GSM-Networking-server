package team.themoment.gsmNetworking.domain.message.domain

import java.util.UUID
import javax.persistence.*


@Entity
@Table(
    name = "header", indexes = [Index(name = "idx_user1_user2", columnList = "user1_id, user2_id", unique = true),
        Index(name = "idx_user2_time", columnList = "user2_id, recent_message_id desc", unique = true),
        Index(name = "idx_user1_time", columnList = "user1_id, recent_message_id desc", unique = true)]
)
// Header는 Message에 의존적임.
// Message의 빠른 조회를 위해 비정규화 된 값 값이라고 봐도 됨
class Header private constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "header_id")
    val headerId: Long = 0,

    @Column(name = "user1_id", nullable = false)
    val user1Id: Long,

    @Column(name = "user2_id", nullable = false)
    val user2Id: Long,

    @Column(name = "recent_message_id", columnDefinition = "BINARY(16)", unique = true, nullable = false)
    val recentMessageId: UUID
) {
    init {
        require(user1Id != user2Id) { "user1Id and user2Id must be different" }
        require(user1Id < user2Id) { "user1Id must be smaller than user2Id" }
    }

    constructor(user1Id: Long, user2Id: Long, recentChatId: UUID) : this(
        headerId = 0,
        user1Id = minOf(user1Id, user2Id),
        user2Id = maxOf(user1Id, user2Id),
        recentMessageId = recentChatId
    )

    fun copyWithNewRecentChatId(header: Header, newRecentChatId: UUID): Header {
        return Header(
            headerId = this.headerId,
            user1Id = this.user1Id,
            user2Id = this.user2Id,
            recentMessageId = newRecentChatId
        )
    }
}
