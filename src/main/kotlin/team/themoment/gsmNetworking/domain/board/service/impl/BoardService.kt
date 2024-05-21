package team.themoment.gsmNetworking.domain.board.service.impl

import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringTemplateEngine
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory.*
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.board.service.QueryBoardInfoUseCase
import team.themoment.gsmNetworking.domain.board.service.SaveBoardUseCase
import team.themoment.gsmNetworking.domain.board.service.QueryBoardListUseCase
import team.themoment.gsmNetworking.domain.comment.domain.Comment
import team.themoment.gsmNetworking.domain.comment.dto.AuthorDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto
import team.themoment.gsmNetworking.domain.comment.dto.ReplyDto
import team.themoment.gsmNetworking.domain.comment.dto.ReplyCommentInfo
import team.themoment.gsmNetworking.domain.comment.repository.CommentRepository
import team.themoment.gsmNetworking.domain.mentor.repository.MentorRepository
import team.themoment.gsmNetworking.domain.popup.domain.Popup
import team.themoment.gsmNetworking.domain.popup.repository.PopupRepository
import team.themoment.gsmNetworking.domain.user.repository.UserRepository
import java.time.LocalDateTime
import javax.mail.internet.MimeMessage

@Service
class BoardService (
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val mentorRepository: MentorRepository,
    private val commentRepository: CommentRepository,
    private val popupRepository: PopupRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val mailSender: JavaMailSender,
    private val templateEngine: ISpringTemplateEngine
) : SaveBoardUseCase,
    QueryBoardListUseCase,
    QueryBoardInfoUseCase {

    @Transactional
    override fun saveBoard(boardSaveDto: BoardSaveDto, authenticationId: Long): BoardListDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val authentication = authenticationRepository.findById(authenticationId)
            .orElseThrow { throw ExpectedException("유저의 권한 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        if (boardSaveDto.boardCategory == TEACHER
            && authentication.authority != Authority.TEACHER) {
            throw ExpectedException("선생님이 아닌 유저는 선생님 카테고리를 이용할 수 없습니다.", HttpStatus.NOT_FOUND)
        }

        val newBoard = Board(
            title = boardSaveDto.title,
            content = boardSaveDto.content,
            boardCategory = boardSaveDto.boardCategory,
            author = currentUser
        )

        val savedBoard = boardRepository.save(newBoard)

        val popupExp = boardSaveDto.popupExp
        if (popupExp != null && boardSaveDto.boardCategory == TEACHER) {
            val currentDateTime = LocalDateTime.now()
            val newPopupExpTime = currentDateTime.plusDays(popupExp.toLong())

            val newPopup = Popup(
                board = savedBoard,
                expTime = newPopupExpTime
            )

            popupRepository.save(newPopup)
        }

        if (savedBoard.boardCategory == TEACHER) {
            sendEmailMentors(savedBoard.id, savedBoard.title)
        }

        return BoardListDto(
            id = savedBoard.id,
            title = savedBoard.title,
            boardCategory = savedBoard.boardCategory,
            author = AuthorDto(
                name = savedBoard.author.name,
                generation = savedBoard.author.generation,
                profileUrl = savedBoard.author.profileUrl,
                defaultImgNumber = savedBoard.author.defaultImgNumber
            ),
            createdAt = savedBoard.createdAt,
            commentCount = 0,
            likeCount = 0,
            isLike = false
        )

    }

    @Transactional(readOnly = true)
    override fun queryBoardList(cursorId: Long,
                                pageSize: Long,
                                boardCategory: BoardCategory?,
                                authenticationId: Long): List<BoardListDto> {

        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)


        return if (cursorId == 0L)
                boardRepository.findPageWithRecentBoard(pageSize, boardCategory, currentUser)
            else
                boardRepository.findPageByCursorId(cursorId, pageSize, boardCategory, currentUser)
    }

    @Transactional(readOnly = true)
    override fun queryBoardInfo(boardId: Long, authenticationId: Long): BoardInfoDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val currentBoard = boardRepository.findById(boardId)
            .orElseThrow { ExpectedException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val findComments = commentRepository.findAllByBoardAndParentCommentIsNull(currentBoard)

        return BoardInfoDto(
            id = currentBoard.id,
            title = currentBoard.title,
            content = currentBoard.content,
            boardCategory = currentBoard.boardCategory,
            author = AuthorDto(
                name = currentBoard.author.name,
                generation = currentBoard.author.generation,
                profileUrl = currentBoard.author.profileUrl,
                defaultImgNumber = currentBoard.author.defaultImgNumber
            ),
            createdAt = currentBoard.createdAt,
            comments = getFindComments(findComments),
            likeCount = currentBoard.likes.size,
            isLike = currentBoard.likes.stream().anyMatch {
                like -> like.user == currentUser
            }
        )
    }

    private fun getFindComments(findComments: List<Comment>): List<CommentListDto> {
        return findComments.map { CommentListDto(
            commentId = it.id,
            comment = it.comment,
            author = AuthorDto(
                name = it.author.name,
                generation = it.author.generation,
                profileUrl = it.author.profileUrl,
                defaultImgNumber = it.author.defaultImgNumber
            ),
            replies = getFindReplies(it)
        ) }
    }

    private fun getFindReplies(parentComment: Comment): List<ReplyDto> {
        return commentRepository.findAllByParentComment(parentComment).map { reply ->
            ReplyDto(
                comment = ReplyCommentInfo(
                    commentId = reply.id,
                    comment = reply.comment,
                    author = AuthorDto(
                        name = reply.author.name,
                        generation = reply.author.generation,
                        profileUrl = reply.author.profileUrl,
                        defaultImgNumber = reply.author.defaultImgNumber
                    ),
                    replyCommentId = reply.repliedComment?.id
                )
            ) }
    }

    private fun sendEmailMentors(teacherBoardId: Long, teacherPostTitle: String) {
        mentorRepository.findAllMentorEmailDto().forEach { mentor ->
            teacherBoardEmailSend(teacherBoardId, teacherPostTitle, mentor.email)
        }
    }

    private fun teacherBoardEmailSend(teacherBoardId: Long, teacherPostTitle: String, sendEmail: String) {
        mailSender.send(getMessage(teacherBoardId, teacherPostTitle, sendEmail))
    }

    private fun getMessage(teacherBoardId: Long, teacherPostTitle: String, sendEmail: String): MimeMessage {
        val message = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, "UTF-8")

        messageHelper.setSubject("GSM-Networking에 새로운 게시글이 등록되었습니다!")
        messageHelper.setText(createMailTemplate(teacherBoardId, teacherPostTitle), true)
        messageHelper.setTo(sendEmail)

        return message
    }

    private fun createMailTemplate(teacherBoardId: Long, teacherPostTitle: String): String {
        val context = Context()

        context.setVariable("teacherBoardId", teacherBoardId)
        context.setVariable("teacherPostTitle", teacherPostTitle)

        return templateEngine.process("email-template", context)
    }

}
