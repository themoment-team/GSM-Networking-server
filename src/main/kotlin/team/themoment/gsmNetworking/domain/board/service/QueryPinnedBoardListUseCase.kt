package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.dto.BoardListDto

interface QueryPinnedBoardListUseCase {

    fun queryPinnedBoardList(authenticationId: Long): List<BoardListDto>
}