package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.domain.Category
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto

interface QueryBoardListUseCase {
    fun queryBoardList(cursorId: Long, pageSize: Long, category: Category?): List<BoardInfoDto>
}
