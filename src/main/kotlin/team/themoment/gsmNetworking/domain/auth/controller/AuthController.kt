package team.themoment.gsmNetworking.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.cookie.CookieUtil
import team.themoment.gsmNetworking.domain.auth.service.ReissueTokenService
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val reissueTokenService: ReissueTokenService
) {

    @PatchMapping("/reissue")
    fun reissueToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Unit> {
        val refreshToken = CookieUtil.getCookieValueOrNull(request.cookies, JwtProperties.REFRESH)
        val tokenDto = reissueTokenService.execute(refreshToken)
        CookieUtil.addTokenCookie(tokenDto, response)
        return ResponseEntity.noContent().build()
    }

}
