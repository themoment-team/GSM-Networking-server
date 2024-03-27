package team.themoment.gsmNetworking.domain.board.repository

import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto

interface BoardCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?): List<BoardInfoDto>
    fun findPageWithRecentBoard(pageSize: Long, boardCategory: BoardCategory?): List<BoardInfoDto>
}
