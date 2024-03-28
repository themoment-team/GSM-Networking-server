package team.themoment.gsmNetworking.domain.board.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.board.service.QueryBoardInfoUseCase
import team.themoment.gsmNetworking.domain.board.service.SaveBoardUseCase
import team.themoment.gsmNetworking.domain.board.service.QueryBoardListUseCase
import team.themoment.gsmNetworking.domain.comment.dto.Author
import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto
import team.themoment.gsmNetworking.domain.comment.dto.Reply
import team.themoment.gsmNetworking.domain.comment.dto.ReplyCommentInfo
import team.themoment.gsmNetworking.domain.comment.repository.CommentRepository
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class BoardService (
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) : SaveBoardUseCase,
    QueryBoardListUseCase,
    QueryBoardInfoUseCase {

    @Transactional
    override fun saveBoard(boardSaveDto: BoardSaveDto, authenticationId: Long): BoardListDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val newBoard = Board(
            title = boardSaveDto.title,
            content = boardSaveDto.content,
            boardCategory = boardSaveDto.boardCategory,
            author = currentUser
        )

        val savedBoard = boardRepository.save(newBoard)

        return BoardListDto(
            id = savedBoard.id,
            title = savedBoard.title,
            boardCategory = savedBoard.boardCategory,
            authorName = savedBoard.author.name,
            createdAt = savedBoard.createdAt
        )

    }

    @Transactional(readOnly = true)
    override fun queryBoardList(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?): List<BoardListDto> =
        if (cursorId == 0L)
            boardRepository.findPageWithRecentBoard(pageSize, boardCategory)
        else
            boardRepository.findPageByCursorId(cursorId, pageSize, boardCategory)

    @Transactional(readOnly = true)
    override fun queryBoardInfo(boardId: Long): BoardInfoDto {
        val currentBoard = boardRepository.findById(boardId)
            .orElseThrow { ExpectedException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND) }

        val currentComments = commentRepository.findAllByBoardAndTopCommentIsNull(currentBoard)

        return BoardInfoDto(
            id = currentBoard.id,
            title = currentBoard.title,
            content = currentBoard.content,
            boardCategory = currentBoard.boardCategory,
            author = Author(
                name = currentBoard.author.name,
                generation = currentBoard.author.generation,
                profileUrl = currentBoard.author.profileUrl
            ),
            createdAt = currentBoard.createdAt,
            comments = currentComments.map {
                CommentListDto(
                    commentId = it.id,
                    comment = it.comment,
                    author = Author(
                        name = it.author.name,
                        generation = it.author.generation,
                        profileUrl = it.author.profileUrl
                    ),
                    replies = commentRepository.findAllByTopComment(it).map { reply ->
                            Reply(
                                comment = ReplyCommentInfo(
                                    commentId = reply.id,
                                    comment = reply.comment,
                                    author = Author(
                                        name = reply.author.name,
                                        generation = reply.author.generation,
                                        profileUrl = reply.author.profileUrl
                                    ),
                                    replyCommentId = reply.repliedComment?.id
                                )
                            )
                    }
                )
            }
        )
    }
}