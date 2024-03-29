package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto

interface SaveBoardUseCase {
    fun saveBoard(boardSaveDto: BoardSaveDto, authenticationId: Long): BoardListDto
}
