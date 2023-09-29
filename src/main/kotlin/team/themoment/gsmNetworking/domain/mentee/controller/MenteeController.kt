package team.themoment.gsmNetworking.domain.mentee.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentee.service.UpdateMenteeService

@RestController
@RequestMapping("api/v1/mentee")
class MenteeController(
    private val updateMenteeService: UpdateMenteeService
) {

    @PostMapping
    fun updateMentee(): ResponseEntity<Map<String, String>> =
        updateMenteeService.execute()
            .let { ResponseEntity.ok(mapOf("message" to "권한을 수정하였습니다. 토큰을 재발급 해주세요.")) }

}