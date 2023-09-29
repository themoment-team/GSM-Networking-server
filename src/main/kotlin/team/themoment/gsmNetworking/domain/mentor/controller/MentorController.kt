package team.themoment.gsmNetworking.domain.mentor.controller

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
    fun mentorRegistration(@RequestBody dto: MentorRegistrationDto): ResponseEntity<Map<String, String>> =
        mentorRegistrationService.execute(dto)
            .let { ResponseEntity.ok(mapOf("message" to "권한을 수정하였습니다. 토큰을 재발급 해주세요.")) }

}