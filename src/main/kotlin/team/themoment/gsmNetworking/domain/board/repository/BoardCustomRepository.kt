package team.themoment.gsmNetworking.domain.board.repository

import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto

interface BoardCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?): List<BoardListDto>
    fun findPageWithRecentBoard(pageSize: Long, boardCategory: BoardCategory?): List<BoardListDto>
}
