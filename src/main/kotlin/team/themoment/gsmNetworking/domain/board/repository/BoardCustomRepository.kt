package team.themoment.gsmNetworking.domain.board.repository

import team.themoment.gsmNetworking.domain.board.domain.Category
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto

interface BoardCustomRepository {
    fun findPageByCursorId(cursorId: Long, pageSize: Long, category: Category?): List<BoardInfoDto>
    fun findPageWithRecentBoard(pageSize: Long, category: Category?): List<BoardInfoDto>
}
