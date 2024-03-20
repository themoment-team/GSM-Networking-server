package team.themoment.gsmNetworking.domain.feed.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "comment")
    val comment: String,

    @OneToOne
    @JoinColumn(name = "feed_id")
    val feed: Feed,

    @OneToOne
    @JoinColumn(name = "reply_comment_id")
    val replyComment: Comment,

    @OneToOne
    @JoinColumn(name = "author_id")
    val user: User,

    val order: Int
): BaseIdTimestampEntity();
