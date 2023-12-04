package team.themoment.gsmNetworking.domain.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.manager.AuthenticatedUserManager
import team.themoment.gsmNetworking.domain.user.dto.ProfileUrlRegistrationDto
import team.themoment.gsmNetworking.domain.user.service.ProfileUrlRegistrationService

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val authenticatedUserManager: AuthenticatedUserManager,
    private val profileUrlRegistrationService: ProfileUrlRegistrationService
) {

    @PostMapping("/profile-url")
    fun profileUrlRegistration(@RequestBody dto: ProfileUrlRegistrationDto): ResponseEntity<Void> {
        val authenticationId = authenticatedUserManager.getName()
        profileUrlRegistrationService.execute(authenticationId, dto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
