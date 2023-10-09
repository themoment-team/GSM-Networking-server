package team.themoment.gsmNetworking.domain.auth.controller

import team.themoment.gsmNetworking.domain.auth.service.ReissueTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val reissueTokenService: ReissueTokenService
) {

    @PatchMapping("/reissue")
    fun reissueToken(@RequestHeader refreshToken: String, response: HttpServletResponse): ResponseEntity<Unit> {
        reissueTokenService.execute(refreshToken, response)
        return ResponseEntity.noContent().build()
    }

}