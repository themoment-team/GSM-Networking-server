package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorListDto
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorListService

@RestController
@RequestMapping("/api/v1/tempMentor")
class TempMentorController (
    private val queryTempMentorListService: QueryTempMentorListService
) {

    @GetMapping
    fun queryTempMentorList(): ResponseEntity<TempMentorListDto> {
        val tempMentorList = queryTempMentorListService.execute()
        return ResponseEntity.ok(tempMentorList)
    }

}
