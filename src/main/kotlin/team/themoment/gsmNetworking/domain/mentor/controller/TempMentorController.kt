package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.service.DeleteTempMentorService
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorListService
import team.themoment.gsmNetworking.domain.mentor.service.QueryTempMentorService
import team.themoment.gsmNetworking.domain.mentor.service.SearchTempMentorListService

@RestController
@RequestMapping("/api/v1/temp-mentor")
class TempMentorController(
    private val queryTempMentorListService: QueryTempMentorListService,
    private val searchTempMentorListService: SearchTempMentorListService,
    private val deleteTempMentorService: DeleteTempMentorService,
    private val queryTempMentorService: QueryTempMentorService
) {

    @GetMapping
    fun queryTempMentorList(): ResponseEntity<List<TempMentorInfoDto>> {
        val tempMentorList = queryTempMentorListService.execute()
        return ResponseEntity.ok(tempMentorList)
    }

    @GetMapping("/{firebaseId}")
    fun findTempMentor(@PathVariable firebaseId: String): ResponseEntity<TempMentorInfoDto> {
        val tempMentor = queryTempMentorService.execute(firebaseId)
        return ResponseEntity.ok(tempMentor)
    }

    @GetMapping("/{name}")
    fun searchTempMentorListByName(@PathVariable name: String): ResponseEntity<List<SearchTempMentorInfoDto>> {
        val searchTempMentorList = searchTempMentorListService.execute(name)
        return ResponseEntity.ok(searchTempMentorList)
    }

    @DeleteMapping("/{firebaseId}")
    fun deleteTempMentor(@PathVariable firebaseId: String): ResponseEntity<Void> {
        deleteTempMentorService.execute(firebaseId)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

}
