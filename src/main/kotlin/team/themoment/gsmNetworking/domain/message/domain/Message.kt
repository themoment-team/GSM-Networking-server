package team.themoment.gsmNetworking.domain.message.domain

import team.themoment.gsmNetworking.common.domain.BaseTimestampEntity
import team.themoment.gsmNetworking.common.util.UUIDUtils
import java.util.UUID
import javax.persistence.*

@Entity
@Table(
    name = "message",
    indexes = [Index(name = "idx_user1_user2", columnList = "user1_id, user2_id, message_id desc", unique = true)]
)
class Message private constructor(
    @Id @Column(name = "message_id", columnDefinition = "BINARY(16)")
    val messageId: UUID = UUIDUtils.generateUUIDv7(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "header_id", nullable = false)
    val header: Header,

    @Column(name = "user1_id", nullable = false)
    val user1Id: Long,

    @Column(name = "user2_id", nullable = false)
    val user2Id: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    val direction: MessageDirection,

    @Column(name = "content", nullable = false)
    val content: String,
) : BaseTimestampEntity() {

    init {
        require(user1Id != user2Id) { "user1Id and user2Id must be different" }
        require(user1Id < user2Id) { "user1Id must be smaller than user2Id" }
    }

    constructor(
        messageId: UUID = UUIDUtils.generateUUIDv7(),
        header: Header,
        direction: MessageDirection,
        content: String
    ) : this(
        messageId = messageId,
        header = header,
        user1Id = header.user1Id,
        user2Id = header.user2Id,
        direction = direction,
        content = content
    )

    enum class MessageDirection {
        ToUser1,
        ToUser2
    }
}
