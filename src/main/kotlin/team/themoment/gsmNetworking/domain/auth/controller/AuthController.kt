package team.themoment.gsmNetworking.domain.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.themoment.gsmNetworking.common.cookie.CookieManager
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.dto.MyTokenDto
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

    @GetMapping("/my-token")
    fun getToken(request: HttpServletRequest): ResponseEntity<MyTokenDto> {
        val token = cookieManager.getCookieValueOrNull(cookies = request.cookies, name = JwtProperties.ACCESS)
            ?: throw ExpectedException("accessToken을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        return ResponseEntity.ok(MyTokenDto(accessToken = token))
    }

}
