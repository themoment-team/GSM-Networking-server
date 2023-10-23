package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.domain.mentor.dto.ExistingMentorListDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto
import team.themoment.gsmNetworking.domain.mentor.service.MentorRegistrationService
import team.themoment.gsmNetworking.domain.mentor.service.QueryExistingMentorListService
import team.themoment.gsmNetworking.domain.mentor.service.QueryMentorListService

@RestController
@RequestMapping("api/v1/mentor")
class MentorController(
    private val mentorRegistrationService: MentorRegistrationService,
    private val queryMentorListService: QueryMentorListService,
    private val queryExistingMentorListService: QueryExistingMentorListService
) {

    @PostMapping
    fun saveMentorInfo(@RequestBody dto: MentorRegistrationDto): ResponseEntity<Void> {
        mentorRegistrationService.execute(dto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun queryMentorList(): ResponseEntity<List<MentorInfoDto>> {
        val mentorList = queryMentorListService.execute()
        return ResponseEntity.ok(mentorList)
    }

    @GetMapping("/existing/{userName}")
    fun queryExistingMentorList(@PathVariable("userName") userName: String): ResponseEntity<ExistingMentorListDto> {
        val existingMentorList = queryExistingMentorListService.execute(userName)
        return ResponseEntity.ok(existingMentorList)
    }

}
