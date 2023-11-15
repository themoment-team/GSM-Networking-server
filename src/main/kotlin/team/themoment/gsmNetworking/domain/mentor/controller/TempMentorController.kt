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
import team.themoment.gsmNetworking.domain.mentor.service.*

@RestController
@RequestMapping("/api/v1/temp-mentor")
class TempMentorController(
    private val queryTempMentorListService: QueryTempMentorListService,
    private val queryTempMentorListByNameService: QueryTempMentorListByNameService,
    private val deleteTempMentorService: DeleteTempMentorService,
    private val queryTempMentorService: QueryTempMentorService
) {

    @GetMapping
    fun queryTempMentorList(): ResponseEntity<List<TempMentorInfoDto>> {
        val tempMentorList = queryTempMentorListService.execute()
        return ResponseEntity.ok(tempMentorList)
    }

    @GetMapping("/{id}")
    fun findTempMentor(@PathVariable id: Long): ResponseEntity<TempMentorInfoDto> {
        val tempMentor = queryTempMentorService.execute(id)
        return ResponseEntity.ok(tempMentor)
    }

    @GetMapping("/search/{name}")
    fun searchTempMentorListByName(@PathVariable name: String): ResponseEntity<List<SearchTempMentorInfoDto>> {
        val searchTempMentorList = queryTempMentorListByNameService.execute(name)
        return ResponseEntity.ok(searchTempMentorList)
    }

    @DeleteMapping("/{id}")
    fun deleteTempMentor(@PathVariable id: Long): ResponseEntity<Void> {
        deleteTempMentorService.execute(id)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

}
