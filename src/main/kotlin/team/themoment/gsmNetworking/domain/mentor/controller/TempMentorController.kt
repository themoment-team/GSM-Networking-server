package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.mentor.dto.SearchTempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.TempMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.service.*

@RestController
@RequestMapping("/api/v1/temp-mentor")
class TempMentorController(
    private val queryAllTempMentorsUseCase: QueryAllTempMentorsUseCase,
    private val queryTempMentorsByNameUseCase: QueryTempMentorsByNameUseCase,
    private val deleteTempMentorByIdUseCase: DeleteTempMentorByIdUseCase,
    private val queryTempMentorByIdUseCase: QueryTempMentorByIdUseCase
) {

    @GetMapping
    fun queryAllTempMentors(): ResponseEntity<List<TempMentorInfoDto>> {
        val tempMentorList = queryAllTempMentorsUseCase.queryAllTempMentors()
        return ResponseEntity.ok(tempMentorList)
    }

    @GetMapping("/{id}")
    fun queryTempMentorById(@PathVariable id: Long): ResponseEntity<TempMentorInfoDto> {
        val tempMentor = queryTempMentorByIdUseCase.queryTempMentorById(id)
        return ResponseEntity.ok(tempMentor)
    }

    @GetMapping("/search/{name}")
    fun queryTempMentorsByName(@PathVariable name: String): ResponseEntity<List<SearchTempMentorInfoDto>> {
        val searchTempMentorList = queryTempMentorsByNameUseCase.queryTempMentorsByName(name)

        if (searchTempMentorList.isEmpty())
            throw ExpectedException("검색 결과가 없습니다.", HttpStatus.NO_CONTENT)
        else
            return ResponseEntity.ok(searchTempMentorList)
    }

    @DeleteMapping("/{id}")
    fun deleteTempMentorById(@PathVariable id: Long): ResponseEntity<Void> {
        deleteTempMentorByIdUseCase.deleteTempMentorById(id)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

}
