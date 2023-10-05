package team.themoment.gsmNetworking.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import team.themoment.gsmNetworking.global.security.principal.AuthenticationDetailsService
import java.security.Key
import javax.servlet.http.HttpServletRequest

/**
 * 토큰을 파싱하는 클래스 입니다.
 */
@Component
class TokenParser(
    private val authenticationDetailsService: AuthenticationDetailsService,
    private val jwtProperties: JwtProperties
) {

    /**
     * access 토큰의 prefix를 파싱하는 메서드 입니다.
     *
     * @param request 요청한 requestServlet
     * @return 지정한 토큰 헤더로 보냈다면 token prefix를 제외한 access token을 반환하고, 지정한 토큰 헤더로 보내지 않을 경우 null을 반환합니다.
     */
    fun parseAccessTokenOrNull(request: HttpServletRequest): String? {
        val accessToken = request.getHeader(JwtProperties.HEADER) ?: return null
        return if (accessToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
                    accessToken.replace(JwtProperties.TOKEN_PREFIX, "")
                } else null
    }

    /**
     * 재발급 토큰의 prefix를 파싱하는 메서드 입니다.
     *
     * @param refreshToken prefix를 포함한 refreshToken 입니다.
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
     *       1. jwt의 토큰 prefix인 Bearer이 붙지 않은 경우
     * @return prefix를 replace한 refreshToken을 반환 합니다.
     */
    fun parseRefreshToken(refreshToken: String): String {
        if (refreshToken.startsWith(JwtProperties.TOKEN_PREFIX))
            return refreshToken.replace(JwtProperties.TOKEN_PREFIX, "")
        throw ExpectedException("유효하지 않은 refresh token 입니다.", HttpStatus.UNAUTHORIZED)
    }

    /**
     * 토큰의 값으로 authentication 객체를 만드는 메서드 입니다.
     *
     * @param accessToken 요청한 accessToken
     * @return authDetails로 생성한 usernamePasswordAuthenticationToken 객체를 반환 합니다.
     */
    fun authentication(accessToken: String): UsernamePasswordAuthenticationToken {
        val authenticationDetails = authenticationDetailsService.loadUserByUsername(getAccessTokenSubject(accessToken))
        return UsernamePasswordAuthenticationToken(authenticationDetails, "", authenticationDetails.authorities)
    }

    /**
     * access 토큰을 파싱하여 subject를 얻는 메서드 입니다.
     *
     * @param accessToken 파싱할 토큰
     * @return access 토큰의 subject
     */
    private fun getAccessTokenSubject(accessToken: String): String =
        getTokenBody(accessToken, jwtProperties.accessSecret).subject

    /**
     * 토큰을 파싱하여 claims를 얻는 메서드 입니다.
     *
     * @param token 파싱할 토큰
     * @param secret 파싱할 토큰의 secret 값
     * @return 토큰의 claims
     */
    private fun getTokenBody(token: String, secret: Key): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body

}