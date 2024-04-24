package team.themoment.gsmNetworking.domain.like.domain

import team.themoment.gsmNetworking.common.domain.BaseIdTimestampEntity
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.user.domain.User
import javax.persistence.*


@Entity
@Table(name = "likes")
class Like (
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "feed_id")
    val board: Board,
): BaseIdTimestampEntity()
