package team.themoment.gsmNetworking.domain.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.cookie.CookieManager
import team.themoment.gsmNetworking.domain.auth.service.ReissueTokenService
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val reissueTokenService: ReissueTokenService,
    private val cookieManager: CookieManager
) {

    @PatchMapping("/reissue")
    fun reissueToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Unit> {
        val refreshToken = cookieManager.getCookieValueOrNull(request.cookies, JwtProperties.REFRESH)
        val tokenDto = reissueTokenService.execute(refreshToken)
        cookieManager.addTokenCookie(tokenDto, response)
        return ResponseEntity.noContent().build()
    }

}
