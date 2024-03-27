package team.themoment.gsmNetworking.domain.comment.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.comment.dto.CommentInfoDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentListDto
import team.themoment.gsmNetworking.domain.comment.dto.CommentSaveDto
import team.themoment.gsmNetworking.domain.comment.service.QueryCommentInfoUseCase
import team.themoment.gsmNetworking.domain.comment.service.SaveCommentUseCase
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/v1/comment")
class CommentController (
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val queryCommentInfoUseCase: QueryCommentInfoUseCase
) {

    @PostMapping
    fun saveComment(@Valid @RequestBody commentSaveDto: CommentSaveDto): ResponseEntity<CommentInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(saveCommentUseCase.saveComment(commentSaveDto, authenticationId))
    }

    @GetMapping
    fun queryComment(@RequestParam commentId: Long): ResponseEntity<CommentListDto> {
            return ResponseEntity.ok(queryCommentInfoUseCase.queryCommentInfo(commentId))
    }

}
