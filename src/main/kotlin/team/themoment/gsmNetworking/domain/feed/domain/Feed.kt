package team.themoment.gsmNetworking.domain.feed.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*
import javax.persistence.FetchType.*

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
    @JoinColumn(name = "author_id")
    val author: User,

    @OneToMany(mappedBy = "feed", fetch = LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: List<Comment> = ArrayList()
): BaseIdTimestampEntity();
