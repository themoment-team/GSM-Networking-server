package team.themoment.gsmNetworking.domain.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.user.dto.*
import team.themoment.gsmNetworking.domain.user.service.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val generateProfileUrlUseCase: GenerateProfileUrlUseCase,
    private val queryUserInfoByUserIdUseCase: QueryUserInfoByUserIdUseCase,
    private val queryEmailByUserIdUseCase: QueryEmailByUserIdUseCase,
    private val queryUserIsTeacherUsecase: QueryUserIsTeacherUsecase,
    private val updateUserProfileNumberUseCase: UpdateUserProfileNumberUseCase
) {

    @PostMapping("/profile-url")
    fun profileUrlRegistration(@RequestBody dto: ProfileUrlRegistrationDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        generateProfileUrlUseCase.generateProfileUrl(authenticationId, dto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/user-id")
    fun queryUserInfo(@RequestParam userId: Long): ResponseEntity<UserSimpleInfoDto> {
        return ResponseEntity.ok(queryUserInfoByUserIdUseCase.queryUserInfoByUserId(userId))
    }

    @GetMapping("/user-email")
    fun queryUserId(@RequestParam email: String): ResponseEntity<UserIdDto> {
        return ResponseEntity.ok(queryEmailByUserIdUseCase.queryEmailByUserId(email))
    }

    @GetMapping("/is-teacher")
    fun queryIsTeacher(): ResponseEntity<UserIsTeacherDto> {
        val authenticationId = authenticatedUserManager.getName()
        return ResponseEntity.ok(queryUserIsTeacherUsecase.queryUserIsTeacher(authenticationId))
    }

    @PatchMapping("/profile-number")
    fun updateProfileNumber(@RequestBody @Valid userProfileNumberDto: UserProfileNumberDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        updateUserProfileNumberUseCase.updateUserProfileNumber(userProfileNumberDto, authenticationId)
        return ResponseEntity.ok().build()
    }

}
