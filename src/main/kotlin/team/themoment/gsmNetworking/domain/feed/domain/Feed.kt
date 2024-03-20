package team.themoment.gsmNetworking.domain.feed.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "feed")
class Feed (
    @Column(name = "title", length = 50)
    val title: String,

    @Column(name = "content", length = 1000)
    val content: String,

    @Enumerated(EnumType.STRING)
    val category: Category,

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User
): BaseIdTimestampEntity();
