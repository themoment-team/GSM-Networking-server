package team.themoment.gsmNetworking.domain.comment.domain

import io.micrometer.core.lang.Nullable
import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.feed.domain.Feed
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "comment")
    val comment: String,

    @ManyToOne
    @JoinColumn(name = "feed_id")
    val feed: Feed,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_comment_id")
    @Nullable
    val topComment: Comment?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_comment_id")
    val replyComment: List<Comment> = ArrayList(),

    @OneToOne
    @JoinColumn(name = "author_id")
    val author: User
): BaseIdTimestampEntity();
