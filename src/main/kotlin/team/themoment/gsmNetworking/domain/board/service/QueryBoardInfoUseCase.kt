package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto

interface QueryBoardInfoUseCase {
    fun queryBoardInfo(boardId: Long): BoardInfoDto
}