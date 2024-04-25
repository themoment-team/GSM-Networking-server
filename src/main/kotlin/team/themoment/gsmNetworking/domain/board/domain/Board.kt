package team.themoment.gsmNetworking.domain.board.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.like.domain.Like
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*
import javax.persistence.FetchType.*

@Entity
@Table(name = "board")
class Board (
    @Column(name = "title", length = 50)
    val title: String,

    @Column(name = "content", length = 1000)
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "board_category")
    val boardCategory: BoardCategory,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: User,

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = ArrayList(),

    @OneToMany(mappedBy = "board", fetch = LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val likes: MutableList<Like> = ArrayList()
): BaseIdTimestampEntity();
