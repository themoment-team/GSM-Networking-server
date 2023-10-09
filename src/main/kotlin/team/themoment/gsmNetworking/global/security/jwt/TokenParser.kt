package team.themoment.gsmNetworking.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import team.themoment.gsmNetworking.global.security.principal.AuthenticationDetailsService
import java.security.Key

/**
 * 토큰을 파싱하는 클래스 입니다.
 */
@Component
class TokenParser(
    private val authenticationDetailsService: AuthenticationDetailsService,
    private val jwtProperties: JwtProperties
) {

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
