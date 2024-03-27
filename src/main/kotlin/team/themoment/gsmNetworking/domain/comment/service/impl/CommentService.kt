package team.themoment.gsmNetworking.domain.comment.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.comment.dto.Author
import team.themoment.gsmNetworking.domain.comment.dto.CommentInfoDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentSaveDto
import team.themoment.gsmNetworking.domain.comment.repository.CommentRepository
import team.themoment.gsmNetworking.domain.comment.service.SaveCommentUseCase
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) : SaveCommentUseCase {

    @Transactional
    override fun saveComment(commentSaveDto: CommentSaveDto, authenticationId: Long): CommentInfoDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val currentBoard = boardRepository.findById(commentSaveDto.boardId)
            .orElseThrow { throw ExpectedException("게시판을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val newComment = Comment(
            comment = commentSaveDto.comment,
            board = currentBoard,
            author = currentUser,
            topComment = commentSaveDto.topCommentId?.let {
                commentRepository.findById(it)
                    .orElse(null)
            }
        )

        val savedComment = commentRepository.save(newComment)

        if (commentSaveDto.replyCommentId != null) {
            val replyComment = commentRepository.findById(commentSaveDto.replyCommentId)
                .orElseThrow { throw ExpectedException("대댓글을 작성할 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

            replyComment.addReply(savedComment)
        }

        return CommentInfoDto(
            commentId = savedComment.id,
            comment = savedComment.comment,
            author = Author (
                name = savedComment.author.name,
                generation = savedComment.author.generation,
                profileUrl = savedComment.author.profileUrl
            )
        )
    }
}
