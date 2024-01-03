package team.themoment.gsmNetworking.domain.mentee.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.service.GenerateMenteeUseCase
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/mentee")
class MenteeController(
    private val generateMenteeUseCase: GenerateMenteeUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @PostMapping("/update")
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

}
