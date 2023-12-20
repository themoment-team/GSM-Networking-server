package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.mentor.dto.*
import team.themoment.gsmNetworking.domain.mentor.service.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/mentor")
class MentorController(
    private val mentorRegistrationService: MentorRegistrationService,
    private val queryAllMentorListService: QueryAllMentorsService,
    private val deleteMyMentorInfoService: DeleteMyMentorInfoService,
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val queryMyMentorService: QueryMyMentorService,
    private val modifyMyMentorInfoService: ModifyMyMentorInfoService,
) {

    @PostMapping
    fun saveMentorInfo(@RequestBody @Valid dto: MentorRegistrationDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        mentorRegistrationService.execute(dto, authenticationId)
        authenticatedUserManager.updateAuthority(Authority.USER)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun queryAllMentorList(): ResponseEntity<List<MentorInfoDto>> {
        val mentorList = queryAllMentorListService.execute()
        return ResponseEntity.ok(mentorList)
    }

    @GetMapping("/my")
    fun queryMyMentorInfo(): ResponseEntity<MyMentorInfoDto> {
        val myMentorInfo = queryMyMentorService.execute(authenticatedUserManager.getName())
        return ResponseEntity.ok(myMentorInfo)
    }

    @DeleteMapping("/my")
    fun deleteMyMentorInfo(): ResponseEntity<Void> {
        deleteMyMentorInfoService.execute(authenticatedUserManager.getName())
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

    @PutMapping("/my")
    fun modifyMyCareerInfo(@RequestBody @Valid dto: MentorUpdateInfoDto): ResponseEntity<Void> {
        modifyMyMentorInfoService.execute(dto)
        return ResponseEntity.noContent().build()
    }

}
