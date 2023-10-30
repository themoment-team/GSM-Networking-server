package team.themoment.gsmNetworking.global.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.cookie.CookieManager
import team.themoment.gsmNetworking.common.cookie.impl.CookieUtil
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.domain.RefreshToken
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import team.themoment.gsmNetworking.global.security.jwt.TokenGenerator
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomUrlAuthenticationSuccessHandler(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenGenerator: TokenGenerator,
    private val jwtExpTimeProperties: JwtExpTimeProperties,
    private val oauth2Properties: Oauth2Properties,
    private val cookieManager: CookieManager
) : AuthenticationSuccessHandler {

    /**
     * 로그인 성공후 실행되는 핸들러 메서드 입니다.
     *
     * @param request 요청받은 requestServlet
     * @param response 응답할 responseServlet
     * @param authentication 현재 만들어진 인증 객체
     */
    @Transactional(rollbackFor = [Exception::class])
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val authenticationId = authentication.name.toLong()
        val authority = Authority.valueOf(authentication.authorities.first().authority)
        val tokenDto = generateTokenAndSave(authenticationId)
        cookieManager.addTokenCookie(tokenDto, response)
        sendRedirectToAuthority(response, authority)
    }

    /**
     * 사용자 식별자로 토큰을 생성하고, refresh token을 저장하는 메서드 입니다.
     *
     * @param authenticationId 사용자를 식별할 id
     * @return 생성된 토큰을 담고 있는 dto
     */
    private fun generateTokenAndSave(authenticationId: Long): TokenDto {
        val tokenDto = tokenGenerator.generateToken(authenticationId)
        saveRefreshToken(tokenDto.refreshToken, authenticationId)
        return tokenDto
    }

    /**
     * refresh token을 redis에 저장합니다.
     *
     * @param token 저장할 token
     * @param authenticationId 사용자를 식별할 id
     */
    private fun saveRefreshToken(token: String, authenticationId: Long) {
        val refreshToken = RefreshToken(
            token = token,
            authenticationId = authenticationId,
            expirationTime = jwtExpTimeProperties.refreshExp
        )
        refreshTokenRepository.save(refreshToken)
    }

    /**
     * 권한별로 본인인증을 하지 않은 사용자는 회원가입 주소로 리다이렉트, 본인인증을 한 사용자는 디폴트 주소로 리다이렉트 한다.
     *
     * @param response 응답할 responseServlet
     * @param authority 사용자의 권한
     */
    private fun sendRedirectToAuthority(response: HttpServletResponse, authority: Authority) {
        response.status = HttpServletResponse.SC_NO_CONTENT
        val redirectUrl = when (authority) {
            Authority.UNAUTHENTICATED -> oauth2Properties.signUpRedirectUrl
            else -> oauth2Properties.defaultRedirectUrl
        }
        response.sendRedirect(redirectUrl)
    }

}
