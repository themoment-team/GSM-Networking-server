package team.themoment.gsmNetworking.domain.board.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.board.domain.BoardCategory
import team.themoment.gsmNetworking.domain.board.dto.*
import team.themoment.gsmNetworking.domain.board.service.*
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
    private val updateBoardUseCase: UpdateBoardUseCase,
    private val updatePinStatusUseCase: UpdatePinStatusUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @PostMapping
    fun saveBoard(
        @Valid @RequestPart("content") boardSaveDto: BoardSaveDto,
        @RequestPart(value = "files", required = false) files: List<MultipartFile?>
    ): ResponseEntity<BoardInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.status(HttpStatus.CREATED).body(saveBoardUseCase.saveBoard(boardSaveDto, files, authenticationId))
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

    @PatchMapping("/{boardId}")
    fun updateBoard(
        @PathVariable boardId: Long,
        @Valid @RequestPart(value = "content", required = true) boardUpdateDto: BoardUpdateDto,
        @RequestPart(value = "files", required = false) files: List<MultipartFile?>
        ) : ResponseEntity<BoardInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(updateBoardUseCase.updateBoard(boardUpdateDto, files, boardId, authenticationId))
    }

    @PatchMapping("/pin/{boardId}")
    fun updatePinStatus(
        @PathVariable boardId: Long,
    ): ResponseEntity<Void> {
        updatePinStatusUseCase.updatePinStatus(boardId)
        return ResponseEntity.ok().build()
    }
}
