package team.themoment.gsmNetworking.domain.board.service.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.domain.Board
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto
import team.themoment.gsmNetworking.domain.board.repository.BoardRepository
import team.themoment.gsmNetworking.domain.board.service.SaveBoardUseCase
import team.themoment.gsmNetworking.domain.board.service.QueryBoardListUseCase
import team.themoment.gsmNetworking.domain.user.repository.UserRepository

@Service
class BoardService (
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
) : SaveBoardUseCase, QueryBoardListUseCase {

    @Transactional
    override fun saveBoard(boardSaveDto: BoardSaveDto, authenticationId: Long): BoardInfoDto {
        val currentUser = userRepository.findByAuthenticationId(authenticationId)
            ?: throw ExpectedException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND)

        val newBoard = Board(
            title = boardSaveDto.title,
            content = boardSaveDto.content,
            boardCategory = boardSaveDto.boardCategory,
            author = currentUser
        )

        val savedBoard = boardRepository.save(newBoard)

        return BoardInfoDto(
            id = savedBoard.id,
            title = savedBoard.title,
            boardCategory = savedBoard.boardCategory,
            authorName = savedBoard.author.name,
            createdAt = savedBoard.createdAt
        )

    }

    override fun queryBoardList(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?): List<BoardInfoDto> =
        if (cursorId == 0L)
            boardRepository.findPageWithRecentBoard(pageSize, boardCategory)
        else
            boardRepository.findPageByCursorId(cursorId, pageSize, boardCategory)

}
