package team.themoment.gsmNetworking.global.security.handler

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.cookie.CookieUtil
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
): AuthenticationSuccessHandler {

    /**
     * oauth 로그인 성공 후 실행되는 메서드 입니다.
     *
     * @param request 요청 받은 servletRequest
     * @param response 응답할 servletResponse
     * @param authentication 생성된 인증 객체
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
        CookieUtil.addTokenCookie(tokenDto, response)
        sendRedirectToAuthority(response, authority)
    }

    /**
     * authenticationId로 토큰을 발급한 후 재발급 토큰을 저장할 메서드를 호출합니다.
     *
     * @param authenticationId 사용자를 식별할 id
     * @return 사용자 식별자 id로 만들어진 token
     */
    private fun generateTokenAndSave(authenticationId: Long): TokenDto {
        val tokenDto = tokenGenerator.generateToken(authenticationId)
        saveRefreshToken(tokenDto.refreshToken, authenticationId)
        return tokenDto
    }

    /**
     * 재발급 토큰, authenticationId를 redis에 저장합니다.
     *
     * @param token 저장할 refreshToken
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
     * 본인인증을 하지 않은 사용자는 회원가입 주소로 리다이렉션 하고, 본인인증을 한 사용자는 루트 주소로 리다이렉션 합니다.
     *
     * @param response 리다이렉트를 할 servletResponse
     * @param authority 권한 별 리다이렉트를 위한 authority
     */
    private fun sendRedirectToAuthority(response: HttpServletResponse, authority: Authority) {
        val redirectUrl = when (authority) {
            Authority.UNAUTHENTICATED -> oauth2Properties.signUpRedirectUrl
            else -> oauth2Properties.defaultRedirectUrl
        }
        response.status = HttpServletResponse.SC_NO_CONTENT
        response.sendRedirect(redirectUrl)
    }

}