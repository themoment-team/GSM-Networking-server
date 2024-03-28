package team.themoment.gsmNetworking.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.comment.domain.Comment

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByParentCommentAndReplyComment(parentComment: Comment, replyComment: Comment): Comment
    fun findAllByParentComment(parentComment: Comment): List<Comment>
    fun findAllByBoardAndParentCommentIsNull(board: Board): List<Comment>
}
