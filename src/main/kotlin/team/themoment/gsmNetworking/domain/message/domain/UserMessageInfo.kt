package team.themoment.gsmNetworking.domain.message.domain

import java.util.UUID
import javax.persistence.*

@Entity
@Table(
    name = "user_message_info",
    indexes = [Index(name = "idx_user_id_opponent_user_id", columnList = "user_id, opponent_user_id", unique = true)]
)
class UserMessageInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_message_info_id")
    val userMessageInfoId: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "opponent_user_id", nullable = false)
    val opponentUserId: Long,

    // 생성되었지만, 특정 사용자외의 메시지를 한번도 확인하지 않은 경우 null 상태를 가짐
    @Column(name = "last_viewed_message_id", columnDefinition = "BINARY(16)")
    val lastViewedMessageId: UUID?
) {
}
