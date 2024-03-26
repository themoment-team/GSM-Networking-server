package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto

interface GenerateBoardUseCase {
    fun generateBoard(boardSaveDto: BoardSaveDto, authenticationId: Long): BoardInfoDto
}
