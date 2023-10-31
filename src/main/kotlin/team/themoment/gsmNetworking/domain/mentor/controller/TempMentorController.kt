package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorListDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorListDto
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorListService
import team.themoment.gsmNetworking.domain.mentor.service.SearchTempMentorListService

@RestController
@RequestMapping("/api/v1/temp-mentor")
class TempMentorController (
    private val queryTempMentorListService: QueryTempMentorListService,
    private val searchTempMentorListService: SearchTempMentorListService
) {

    @GetMapping
    fun queryTempMentorList(): ResponseEntity<TempMentorListDto> {
        val tempMentorList = queryTempMentorListService.execute()
        return ResponseEntity.ok(tempMentorList)
    }

    @GetMapping("/{name}")
    fun searchTempMentorListByName(@PathVariable name: String): ResponseEntity<SearchTempMentorListDto>{
        val searchTempMentorList = searchTempMentorListService.execute(name)
        return ResponseEntity.ok(searchTempMentorList)
    }

}
