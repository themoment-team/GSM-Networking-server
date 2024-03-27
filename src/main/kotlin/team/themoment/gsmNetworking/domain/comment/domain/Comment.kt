package team.themoment.gsmNetworking.domain.comment.domain

import io.micrometer.core.lang.Nullable
import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Column(name = "comment", columnDefinition = "TEXT", nullable = false)
    val comment: String,

    @ManyToOne
    @JoinColumn(name = "board_id")
    val board: Board,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_comment_id")
    @Nullable
    val topComment: Comment?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_comment_id")
    val replyComment: MutableList<Comment> = ArrayList(),

    @OneToOne
    @JoinColumn(name = "author_id")
    val author: User
): BaseIdTimestampEntity() {
    fun addReply(reply: Comment) {
        this.replyComment.add(reply)
    }
}
