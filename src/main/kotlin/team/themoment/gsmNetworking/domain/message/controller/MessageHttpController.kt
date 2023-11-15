package team.themoment.gsmNetworking.domain.message.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.message.dto.api.req.*
import team.themoment.gsmNetworking.domain.message.dto.domain.HeaderDto
import team.themoment.gsmNetworking.domain.message.service.QueryMessageService

@RestController
@RequestMapping("api/v1/message")
class MessageHttpController(
    private val queryMessageService: QueryMessageService
) {
    @GetMapping("/header")
    fun getMetaMessage(@RequestBody req: QueryHeaderReq): ResponseEntity<HeaderDto?> {
        val headerDto = queryMessageService.getHeaderByUserIds(req.user1Id, req.user2Id)
        return if (headerDto == null) {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.OK).body(headerDto)
        }
    }
}
