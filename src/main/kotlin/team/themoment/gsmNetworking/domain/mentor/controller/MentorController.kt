package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.mentor.dto.MentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.dto.MentorRegistrationDto
import team.themoment.gsmNetworking.domain.mentor.dto.ProfileMentorInfoDto
import team.themoment.gsmNetworking.domain.mentor.service.DeleteMyMentorInfoService
import team.themoment.gsmNetworking.domain.mentor.service.MentorRegistrationService
import team.themoment.gsmNetworking.domain.mentor.service.QueryAllMentorsService
import team.themoment.gsmNetworking.domain.mentor.service.QueryMyMentorService

@RestController
@RequestMapping("api/v1/mentor")
class MentorController(
    private val mentorRegistrationService: MentorRegistrationService,
    private val queryAllMentorsService: QueryAllMentorsService,
    private val deleteMyMentorInfoService: DeleteMyMentorInfoService,
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val queryMyMentorService: QueryMyMentorService
) {

    @PostMapping
    fun saveMentorInfo(@RequestBody dto: MentorRegistrationDto): ResponseEntity<Void> {
        mentorRegistrationService.mentorRegistrationExecute(dto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun queryAllMentorList(): ResponseEntity<List<MentorInfoDto>> {
        val mentorList = queryAllMentorsService.queryAllMentorsExecute()
        return ResponseEntity.ok(mentorList)
    }

    @GetMapping("/my")
    fun queryMyMentorInfo(): ResponseEntity<ProfileMentorInfoDto> {
        val myMentorInfo = queryMyMentorService.execute(authenticatedUserManager.getName())
        return ResponseEntity.ok(myMentorInfo)
    }

    @DeleteMapping("/my")
    fun deleteMyMentorInfo(): ResponseEntity<Void> {
        deleteMyMentorInfoService.execute(authenticatedUserManager.getName())
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

}
