package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto

interface QueryBoardListUseCase {
    fun queryBoardList(cursorId: Long, pageSize: Long, boardCategory: BoardCategory?, authenticationId: Long): List<BoardListDto>
}
