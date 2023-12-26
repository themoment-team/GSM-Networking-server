package team.themoment.gsmNetworking.domain.mentee.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.mentee.dto.MenteeRegistrationDto
import team.themoment.gsmNetworking.domain.mentee.service.MenteeRegistrationService
import team.themoment.gsmNetworking.domain.mentee.service.UpdateMenteeService
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/mentee")
class MenteeController(
    private val updateMenteeService: UpdateMenteeService,
    private val menteeRegistrationService: MenteeRegistrationService,
    private val authenticatedUserManager: AuthenticatedUserManager
) {

    @PatchMapping("/update")
    fun updateMentee(): ResponseEntity<Unit> =
        updateMenteeService.execute()
            .let { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PostMapping
    fun saveMenteeInfo(
        @Valid @RequestBody dto: MenteeRegistrationDto
    ): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        menteeRegistrationService.execute(dto, authenticationId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}
