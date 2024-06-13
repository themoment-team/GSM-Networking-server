package team.themoment.gsmNetworking.domain.comment.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.comment.dto.*
import team.themoment.gsmNetworking.domain.comment.repository.CommentRepository
import team.themoment.gsmNetworking.domain.comment.service.QueryCommentInfoUseCase
import team.themoment.gsmNetworking.domain.comment.service.SaveCommentUseCase
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) : SaveCommentUseCase, QueryCommentInfoUseCase {

    @Transactional
    override fun saveComment(commentSaveDto: CommentSaveDto, authenticationId: Long): CommentInfoDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        if (commentSaveDto.replyCommentId != null && commentSaveDto.parentCommentId == null) {
            throw ExpectedException("최상위 댓글 없이 댓글의 답장을 작성할 수 없습니다.", HttpStatus.BAD_REQUEST)
        }

        val currentBoard = boardRepository.findById(commentSaveDto.boardId)
            .orElseThrow { throw ExpectedException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val newComment = Comment(
            comment = commentSaveDto.comment,
            board = currentBoard,
            author = currentUser,
            parentComment = commentSaveDto.parentCommentId?.let {
                commentRepository.findById(it)
                    .orElse(null)
            }
        )

        val savedComment = commentRepository.save(newComment)

        if (commentSaveDto.replyCommentId != null) {
            val replyComment = commentRepository.findById(commentSaveDto.replyCommentId)
                .orElseThrow { throw ExpectedException("댓글의 답장을 작성할 댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

            newComment.addRepliedComment(replyComment)

            commentRepository.save(replyComment)
        }

        return CommentInfoDto(
            commentId = savedComment.id,
            comment = savedComment.comment,
            author = AuthorDto (
                id = savedComment.author.id,
                name = savedComment.author.name,
                generation = savedComment.author.generation,
                profileUrl = savedComment.author.profileUrl,
                defaultImgNumber = savedComment.author.defaultImgNumber
            )
        )
    }

    @Transactional(readOnly = true)
    override fun queryCommentInfo(commentId: Long): CommentListDto {
        val comment = commentRepository.findById(commentId)
            .orElseThrow { throw ExpectedException("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val findReplies = commentRepository.findAllByParentComment(comment)

        return CommentListDto(
            commentId = comment.id,
            comment = comment.comment,
            author = AuthorDto(
                id = comment.author.id,
                name = comment.author.name,
                generation = comment.author.generation,
                profileUrl = comment.author.profileUrl,
                defaultImgNumber = comment.author.defaultImgNumber
            ),
            replies = getReplies(findReplies)
        )
    }

    private fun getReplies(findReplies: List<Comment>): List<ReplyDto> {
        return findReplies.map { ReplyDto(
            comment = ReplyCommentInfo(
                commentId = it.id,
                comment = it.comment,
                author = AuthorDto(
                    id = it.author.id,
                    name = it.author.name,
                    generation = it.author.generation,
                    profileUrl = it.author.profileUrl,
                    defaultImgNumber = it.author.defaultImgNumber
                ),
                replyCommentId = it.repliedComment?.id
            )
        ) }
    }
}
