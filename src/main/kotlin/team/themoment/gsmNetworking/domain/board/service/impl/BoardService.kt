package team.themoment.gsmNetworking.domain.board.service.impl

import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.ISpringTemplateEngine
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory.*
import team.themoment.gsmNetworking.domain.board.domain.File
import team.themoment.gsmNetworking.domain.board.dto.*
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.board.repository.FileRepository
import team.themoment.gsmNetworking.domain.board.service.*
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
import team.themoment.gsmNetworking.thirdParty.aws.s3.service.DeleteS3FileUseCase
import team.themoment.gsmNetworking.thirdParty.aws.s3.service.FileUploadUseCase
import java.time.LocalDateTime
import javax.mail.internet.MimeMessage

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val mentorRepository: MentorRepository,
    private val commentRepository: CommentRepository,
    private val popupRepository: PopupRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val mailSender: JavaMailSender,
    private val templateEngine: ISpringTemplateEngine,
    private val fileUploadUseCase: FileUploadUseCase,
    private val deleteS3FileUseCase: DeleteS3FileUseCase,
    private val fileRepository: FileRepository,
) : SaveBoardUseCase,
    QueryBoardListUseCase,
    QueryBoardInfoUseCase,
    UpdatePinStatusUseCase,
    UpdateBoardUseCase {
    @Transactional
    override fun saveBoard(
        boardSaveDto: BoardSaveDto,
        files: List<MultipartFile>,
        authenticationId: Long,
    ): BoardInfoDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val authentication = authenticationRepository.findById(authenticationId)
            .orElseThrow { throw ExpectedException("유저의 권한 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        if (boardSaveDto.boardCategory == TEACHER
            && authentication.authority != Authority.TEACHER
        ) {
            throw ExpectedException("선생님이 아닌 유저는 선생님 카테고리를 이용할 수 없습니다.", HttpStatus.NOT_FOUND)
        }


        val newBoard = Board(
            title = boardSaveDto.title,
            content = boardSaveDto.content,
            boardCategory = boardSaveDto.boardCategory,
            author = currentUser,
        )

        val savedBoard = boardRepository.save(newBoard)

        var fileInfoDtoList = emptyList<FileInfoDto>()

        if (files.isNotEmpty()){
            fileInfoDtoList = uploadAndSaveFile(files.filterNotNull(), savedBoard)
        }

        generatePopup(boardSaveDto.popupExp, boardSaveDto.boardCategory, savedBoard)

        return BoardInfoDto(
            id = savedBoard.id,
            title = savedBoard.title,
            content = savedBoard.content,
            boardCategory = savedBoard.boardCategory,
            author = BoardAuthorDto(
                id = savedBoard.author.id,
                name = savedBoard.author.name,
                generation = savedBoard.author.generation,
                profileUrl = savedBoard.author.profileUrl,
                defaultImgNumber = savedBoard.author.defaultImgNumber,
                phoneNumber = savedBoard.author.phoneNumber
            ),
            createdAt = savedBoard.createdAt,
            comments = listOf(),
            likeCount = 0,
            isLike = false,
            isPinned = savedBoard.isPinned,
            fileList = fileInfoDtoList
        )

    }

    @Transactional(readOnly = true)
    override fun queryBoardList(
        cursorId: Long,
        pageSize: Long,
        boardCategory: BoardCategory?,
        authenticationId: Long,
    ): List<BoardListDto> {

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

        val currentFiles = fileRepository.findFilesByBoard(currentBoard)

        val findComments = commentRepository.findAllByBoardAndParentCommentIsNull(currentBoard)

        return BoardInfoDto(
            id = currentBoard.id,
            title = currentBoard.title,
            content = currentBoard.content,
            boardCategory = currentBoard.boardCategory,
            author = BoardAuthorDto(
                id = currentBoard.author.id,
                name = currentBoard.author.name,
                generation = currentBoard.author.generation,
                profileUrl = currentBoard.author.profileUrl,
                defaultImgNumber = currentBoard.author.defaultImgNumber,
                phoneNumber = currentBoard.author.phoneNumber
            ),
            createdAt = currentBoard.createdAt,
            comments = getFindComments(findComments),
            likeCount = currentBoard.likes.size,
            isLike = currentBoard.likes.stream().anyMatch { like ->
                like.user == currentUser
            },
            isPinned = currentBoard.isPinned,
            fileList = ofFileInfoDtoList(currentFiles)
        )
    }

    private fun getFindComments(findComments: List<Comment>): List<CommentListDto> {
        return findComments.map {
            CommentListDto(
                commentId = it.id,
                comment = it.comment,
                author = AuthorDto(
                    id = it.author.id,
                    name = it.author.name,
                    generation = it.author.generation,
                    profileUrl = it.author.profileUrl,
                    defaultImgNumber = it.author.defaultImgNumber
                ),
                replies = getFindReplies(it)
            )
        }
    }


    private fun getFindReplies(parentComment: Comment): List<ReplyDto> {
        return commentRepository.findAllByParentComment(parentComment).map { reply ->
            ReplyDto(
                comment = ReplyCommentInfo(
                    commentId = reply.id,
                    comment = reply.comment,
                    author = AuthorDto(
                        id = reply.author.id,
                        name = reply.author.name,
                        generation = reply.author.generation,
                        profileUrl = reply.author.profileUrl,
                        defaultImgNumber = reply.author.defaultImgNumber
                    ),
                    replyCommentId = reply.repliedComment?.id
                )
            )
        }
    }

    private fun sendEmailToMentors(boardId: Long, postTitle: String) {
        mentorRepository.findAllMentorEmailDto().forEach { mentor ->
            sendMail(boardId, postTitle, mentor.email)
        }
    }

    private fun sendMail(boardId: Long, postTitle: String, toEmail: String) {
        mailSender.send(getMessage(boardId, postTitle, toEmail))
    }

    private fun getMessage(boardId: Long, postTitle: String, toEmail: String): MimeMessage {
        val message = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, "UTF-8")

        messageHelper.setSubject("GSM-Networking에 새로운 게시글이 등록되었습니다!")
        messageHelper.setText(createMailTemplate(boardId, postTitle), true)
        messageHelper.setTo(toEmail)

        return message
    }

    private fun createMailTemplate(boardId: Long, postTitle: String): String {
        val context = Context()

        context.setVariable("teacherBoardId", boardId)
        context.setVariable("teacherPostTitle", postTitle)

        return templateEngine.process("email-template", context)
    }

    @Transactional
    override fun updatePinStatus(boardId: Long) {
        val board = boardRepository.findById(boardId)
            .orElseThrow { throw ExpectedException("존재하지않는 게시글입니다.", HttpStatus.NOT_FOUND) }

        val pinnedBoards = boardRepository.findBoardsByIsPinnedTrue()

        if (pinnedBoards.size >= 3 && !board.isPinned) {
            val oldPinnedBoard = pinnedBoards.last()
            oldPinnedBoard.isPinned = false
        }

        board.isPinned = !board.isPinned
    }

    @Transactional
    override fun updateBoard(
        updateBoardDto: BoardUpdateDto,
        files: List<MultipartFile>,
        boardId: Long,
        authenticationId: Long,
    ): BoardInfoDto {
        val board = boardRepository.findById(boardId)
            .orElseThrow { throw ExpectedException("존재하지않는 게시글입니다.", HttpStatus.NOT_FOUND) }

        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        if (board.author.authenticationId != authenticationId)
            throw ExpectedException("게시글의 작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST)

        val updatedBoard = Board(
            id = board.id,
            createdAt = board.createdAt,
            updatedAt = LocalDateTime.now(),
            title = updateBoardDto.title,
            content = updateBoardDto.content,
            boardCategory = updateBoardDto.boardCategory,
            author = currentUser,
            comments = board.comments,
            likes = board.likes,
            isPinned = board.isPinned
        )


        val saveBoard = boardRepository.save(updatedBoard)

        generatePopup(updateBoardDto.popupExp, updateBoardDto.boardCategory, saveBoard)

        fileRepository.deleteAllByBoard(saveBoard)

        val currentFiles = fileRepository.findFilesByBoard(saveBoard)

        deleteFile(currentFiles)

        var fileInfoDtoList = emptyList<FileInfoDto>()

        if (files.isNotEmpty()){
            fileInfoDtoList = uploadAndSaveFile(files, saveBoard)
        }

        return BoardInfoDto(
            id = saveBoard.id,
            title = saveBoard.title,
            content = saveBoard.content,
            boardCategory = saveBoard.boardCategory,
            author = BoardAuthorDto(
                id = saveBoard.author.id,
                name = saveBoard.author.name,
                generation = saveBoard.author.generation,
                profileUrl = saveBoard.author.profileUrl,
                defaultImgNumber = saveBoard.author.defaultImgNumber,
                phoneNumber = saveBoard.author.phoneNumber
            ),
            comments = getFindComments(saveBoard.comments),
            likeCount = saveBoard.likes.size,
            createdAt = saveBoard.createdAt,
            isPinned = saveBoard.isPinned,
            isLike = saveBoard.likes.stream().anyMatch { like ->
                like.user == currentUser
            },
            fileList = fileInfoDtoList
        )
    }

    private fun generatePopup(popupExp: Int?, category: BoardCategory, board: Board) {
        if (popupExp != null && category == TEACHER) {
            val currentDateTime = LocalDateTime.now()
            val newPopupExpTime = currentDateTime.plusDays(popupExp.toLong())

            val newPopup = Popup(
                board = board,
                expTime = newPopupExpTime
            )

            popupRepository.save(newPopup)
        }
    }

    private fun uploadAndSaveFile(files: List<MultipartFile>, board: Board): List<FileInfoDto> {
        val fileObjects = fileUploadUseCase.fileUpload(files, board)

        val savedFiles = fileRepository.saveAll(fileObjects)

        return ofFileInfoDtoList(savedFiles)
    }

    private fun ofFileInfoDtoList(files: List<File>): List<FileInfoDto> {
        return files.map {
            FileInfoDto(
                it.id,
                it.fileName,
                it.fileUrl
            )
        }
    }

    private fun deleteFile(files: List<File>) {
        files.forEach {
            deleteS3FileUseCase.deleteS3File(it.fileUrl)
        }

        fileRepository.deleteAll(files)
    }

}
