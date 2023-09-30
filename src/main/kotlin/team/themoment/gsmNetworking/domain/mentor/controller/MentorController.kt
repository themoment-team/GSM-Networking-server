package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto
import team.themoment.gsmNetworking.domain.mentor.service.MentorRegistrationService

@RestController
@RequestMapping("api/v1/mentor")
class MentorController(
    private val mentorRegistrationService: MentorRegistrationService
) {

    @PostMapping
    fun mentorRegistration(@RequestBody dto: MentorRegistrationDto): ResponseEntity<Void> =
        mentorRegistrationService.execute(dto)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

}