package team.themoment.gsmNetworking.domain.auth.controller

import team.themoment.gsmNetworking.domain.auth.service.ReissueTokenService
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val reissueTokenService: ReissueTokenService
) {

    @PatchMapping("/reissue")
    fun reissueToken(@RequestHeader refreshToken: String): ResponseEntity<TokenResponse> =
        reissueTokenService.execute(refreshToken)
            .let { ResponseEntity.ok(it) }

}