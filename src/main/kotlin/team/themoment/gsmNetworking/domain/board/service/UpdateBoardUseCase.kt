package team.themoment.gsmNetworking.domain.board.service

import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardUpdateDto

interface UpdateBoardUseCase {
    fun updateBoard(updateBoardDto: BoardUpdateDto, files: List<MultipartFile?>, boardId: Long, authenticationId: Long): BoardInfoDto
}
