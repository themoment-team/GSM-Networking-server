package team.themoment.gsmNetworking.domain.board.service

import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto

interface SaveBoardUseCase {
    fun saveBoard(boardSaveDto: BoardSaveDto, files: List<MultipartFile?>, authenticationId: Long): BoardInfoDto
}
