package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorListService

@RestController
@RequestMapping("/api/v1/tempMentor")
class TempMentorController (
        private val queryTempMentorListService: QueryTempMentorListService
) {

    @GetMapping("/{userName}")
    fun existingMentorList(@PathVariable("userName") userName: String): ResponseEntity<ExistingMentorListDto> {
        val existingMentorList = queryTempMentorListService.execute(userName)
        return ResponseEntity.ok(existingMentorList)
    }

}
