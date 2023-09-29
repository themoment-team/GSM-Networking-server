package team.themoment.gsmNetworking.domain.mentee.controller

import org.springframework.http.HttpStatus
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
    fun updateMentee(): ResponseEntity<Void> =
        updateMenteeService.execute()
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

}