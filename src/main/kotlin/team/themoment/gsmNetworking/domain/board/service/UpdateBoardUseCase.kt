package team.themoment.gsmNetworking.domain.board.service

import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardUpdateDto

interface UpdateBoardUseCase {
    fun updateBoard(updateBoardDto: BoardUpdateDto, boardId: Long, authenticationId: Long): BoardInfoDto
}
