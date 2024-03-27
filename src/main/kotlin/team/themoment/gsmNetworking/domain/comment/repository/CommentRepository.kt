package team.themoment.gsmNetworking.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import team.themoment.gsmNetworking.domain.comment.domain.Comment

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByTopCommentAndReplyComment(topComment: Comment, replyComment: Comment)
}