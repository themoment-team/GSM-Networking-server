package team.themoment.gsmNetworking.domain.board.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.board.domain.Category
import team.themoment.gsmNetworking.domain.board.dto.BoardInfoDto
import team.themoment.gsmNetworking.domain.board.dto.BoardSaveDto
import team.themoment.gsmNetworking.domain.board.service.GenerateBoardUseCase
import team.themoment.gsmNetworking.domain.board.service.QueryBoardListUseCase
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/board")
class BoardController (
    private val generateBoardUseCase: GenerateBoardUseCase,
    private val queryBoardListUseCase: QueryBoardListUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @PostMapping
    fun generateBoard(@Valid @RequestBody boardSaveDto: BoardSaveDto): ResponseEntity<BoardInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.status(HttpStatus.CREATED).body(generateBoardUseCase.generateBoard(boardSaveDto, authenticationId))
    }

    @GetMapping
    fun queryBoardList(@RequestParam cursorId: Long,
                      @RequestParam pageSize: Long,
                      @RequestParam(required = false) category: Category?) : ResponseEntity<List<BoardInfoDto>> {

        if (cursorId < 0L || pageSize < 0L)
            throw ExpectedException("0이상부터 가능합니다.", HttpStatus.BAD_REQUEST)
        if (pageSize > 20L)
            throw ExpectedException("페이지 크기는 20이하까지 가능합니다.", HttpStatus.BAD_REQUEST)

        return ResponseEntity.ok(queryBoardListUseCase.queryBoardList(cursorId, pageSize, category))
    }

}
