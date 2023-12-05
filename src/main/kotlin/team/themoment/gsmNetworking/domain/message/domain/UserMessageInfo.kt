package team.themoment.gsmNetworking.domain.message.domain

import team.themoment.gsmNetworking.common.domain.BaseIdEntity
import javax.persistence.*

@Entity
@Table(
    name = "user_message_info",
    indexes = [Index(name = "idx_user_id_opponent_user_id", columnList = "user_id, opponent_user_id", unique = true)]
)
class UserMessageInfo(
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "opponent_user_id", nullable = false)
    val opponentUserId: Long,

    @Column(name = "last_viewed_time")
    val lastViewedEpochMilli: Long = 0
) : BaseIdEntity() {
    fun updateLastViewedEpochMilli(newLastViewedEpochMilli: Long): UserMessageInfo {
        return UserMessageInfo(
            userId = this.userId,
            opponentUserId = this.opponentUserId,
            lastViewedEpochMilli = newLastViewedEpochMilli
        )
    }
}
