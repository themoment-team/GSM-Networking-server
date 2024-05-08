package team.themoment.gsmNetworking.domain.board.repository

import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.user.domain.User

interface BoardCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?, user: User): List<BoardListDto>
    fun findPageWithRecentBoard(pageSize: Long, boardCategory: BoardCategory?, user: User): List<BoardListDto>
}
