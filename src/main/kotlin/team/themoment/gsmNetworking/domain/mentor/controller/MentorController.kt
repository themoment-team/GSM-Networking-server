package team.themoment.gsmNetworking.domain.mentor.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    private val mentorRegistrationUseCase: MentorRegistrationUseCase,
    private val queryAllMentorsUseCase: QueryAllMentorsUseCase,
    private val queryMentorInfoByIdUseCase: QueryMentorInfoByIdUseCase,
    private val deleteMentorInfoByIdUseCase: DeleteMentorInfoByIdUseCase,
    private val modifyMentorInfoByIdUseCase: ModifyMentorInfoByIdUseCase,
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val generateCompanyAddressUseCase: GenerateCompanyAddressUseCase
) {

    @PostMapping
    fun saveMentorInfo(@RequestBody @Valid dto: MentorSaveInfoDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        mentorRegistrationUseCase.mentorRegistration(dto, authenticationId)
        authenticatedUserManager.updateAuthority(Authority.USER)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun queryAllMentorList(): ResponseEntity<List<MentorInfoDto>> {
        val mentorList = queryAllMentorsUseCase.queryAllMentors()
        return ResponseEntity.ok(mentorList)
    }

    @GetMapping("/my")
    fun queryMyMentorInfo(): ResponseEntity<MyMentorInfoDto> {
        val myMentorInfo = queryMentorInfoByIdUseCase.queryMentorInfoById(authenticatedUserManager.getName())
        return ResponseEntity.ok(myMentorInfo)
    }

    @DeleteMapping("/my")
    fun deleteMyMentorInfo(): ResponseEntity<Void> {
        deleteMentorInfoByIdUseCase.deleteMentorInfoById(authenticatedUserManager.getName())
        authenticatedUserManager.updateAuthority(Authority.TEMP_USER)
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build()
    }

    @PutMapping("/my")
    fun modifyMyMentorInfo(@RequestBody @Valid dto: MentorSaveInfoDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        modifyMentorInfoByIdUseCase.modifyMentorInfoById(authenticationId, dto)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/career/company-address")
    fun companyAddressRegistration(@RequestBody @Valid dto: CompanyAddressRegistrationDto): ResponseEntity<Void> {
        generateCompanyAddressUseCase.generateCompanyAddress(dto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}
