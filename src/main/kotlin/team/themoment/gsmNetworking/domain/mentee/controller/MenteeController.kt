package team.themoment.gsmNetworking.domain.mentee.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeInfoDto
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.service.DeleteMyMenteeInfoUseCase
import team.themoment.gsmNetworking.domain.mentee.service.GenerateMenteeUseCase
import team.themoment.gsmNetworking.domain.mentee.service.QueryMyMenteeInfoUseCase
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/mentee")
class MenteeController(
    private val generateMenteeUseCase: GenerateMenteeUseCase,
    private val deleteMyMenteeInfoUseCase: DeleteMyMenteeInfoUseCase,
    private val queryMyMenteeInfoUseCase: QueryMyMenteeInfoUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @GetMapping("/my")
    fun getMyMenteeInfo(): ResponseEntity<MenteeInfoDto> {
        val authenticationId = authenticatedUserManager.getName()
        val menteeInfoDto = queryMyMenteeInfoUseCase.queryMyMenteeInfo(authenticationId)
        return ResponseEntity.ok(menteeInfoDto)
    }

    @PatchMapping("/update")
    fun updateMentee(): ResponseEntity<Unit> {
        authenticatedUserManager.updateAuthority(Authority.TEMP_USER)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PostMapping
    fun saveMenteeInfo(
        @Valid @RequestBody dto: MenteeRegistrationDto
    ): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        generateMenteeUseCase.generateMentee(dto, authenticationId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/my")
    fun deleteMentee(): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        deleteMyMenteeInfoUseCase.deleteMyMenteeInfo(authenticationId)
        authenticatedUserManager.updateAuthority(Authority.UNAUTHENTICATED)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }
}
