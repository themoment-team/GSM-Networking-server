package team.themoment.gsmNetworking.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.comment.domain.Comment

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByTopCommentAndReplyComment(topComment: Comment, replyComment: Comment): Comment
    fun findAllByTopComment(topComment: Comment): List<Comment>
    fun findAllByBoard(board: Board): List<Comment>
}