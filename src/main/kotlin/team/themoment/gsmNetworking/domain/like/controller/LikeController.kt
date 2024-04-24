package team.themoment.gsmNetworking.domain.like.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.like.dto.LikeStatusDto
import team.themoment.gsmNetworking.domain.like.dto.LikeToggleDto
import team.themoment.gsmNetworking.domain.like.service.ToggleLikeUseCase
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/like")
class LikeController (
    private val toggleLikeUseCase: ToggleLikeUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {
    @PostMapping
    fun toggleLike(@RequestBody @Valid likeToggleDto: LikeToggleDto): ResponseEntity<LikeStatusDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(toggleLikeUseCase.likeToggle(likeToggleDto.boardId, authenticationId))
    }
}
