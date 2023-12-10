package team.themoment.gsmNetworking.domain.message.domain

import javax.persistence.*

@Entity
@Table
class UserMessageInfo(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "header_id", nullable = false)
    val header: Header,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "opponent_user_id", nullable = false)
    val opponentUserId: Long,

    @Column(name = "last_viewed_time")
    val lastViewedEpochMilli: Long = 0
) : BaseIdTimestampEntity() {
    fun updateLastViewedEpochMilli(newLastViewedEpochMilli: Long): UserMessageInfo {
        return UserMessageInfo(
            header = this.header,
            userId = this.userId,
            opponentUserId = this.opponentUserId,
            lastViewedEpochMilli = newLastViewedEpochMilli
        )
    }
}
