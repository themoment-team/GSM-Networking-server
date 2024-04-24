package team.themoment.gsmNetworking.domain.like.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.like.domain.Like
import team.themoment.gsmNetworking.domain.user.domain.User

interface LikeRepository: JpaRepository<Like, Long> {
    fun findByUserAndBoard(user: User, board: Board): Like?
}
