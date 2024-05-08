package team.themoment.gsmNetworking.domain.board.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardListDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto
import team.themoment.gsmNetworking.domain.board.service.QueryBoardInfoUseCase
import team.themoment.gsmNetworking.domain.board.service.SaveBoardUseCase
import team.themoment.gsmNetworking.domain.board.service.QueryBoardListUseCase
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/api/v1/board")
class BoardController (
    private val saveBoardUseCase: SaveBoardUseCase,
    private val queryBoardListUseCase: QueryBoardListUseCase,
    private val queryBoardInfoUseCase: QueryBoardInfoUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @PostMapping
    fun saveBoard(@Valid @RequestBody boardSaveDto: BoardSaveDto): ResponseEntity<BoardListDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.status(HttpStatus.CREATED).body(saveBoardUseCase.saveBoard(boardSaveDto, authenticationId))
    }

    @GetMapping
    fun queryBoardList(
        @Min(value = 0, message = "cursorId는 0 이상이어야 합니다.") @RequestParam cursorId: Long,
        @Min(value = 0, message = "pageSize는 0 이상이어야 합니다.") @Max(value = 20, message = "pageSize는 20 이하여야 합니다.") @RequestParam pageSize: Long,
        @RequestParam(required = false) boardCategory: BoardCategory?
    ) : ResponseEntity<List<BoardListDto>> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(queryBoardListUseCase.queryBoardList(cursorId, pageSize, boardCategory, authenticationId))
    }

    @GetMapping("/{boardId}")
    fun queryBoardInfo(@PathVariable boardId: Long): ResponseEntity<BoardInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(queryBoardInfoUseCase.queryBoardInfo(boardId, authenticationId))
    }

}
