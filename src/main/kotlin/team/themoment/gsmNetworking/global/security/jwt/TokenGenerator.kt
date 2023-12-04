package team.themoment.gsmNetworking.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import java.util.*

@Component
class TokenGenerator(
    private val jwtProperties: JwtProperties,
    private val jwtExpTimeProperties: JwtExpTimeProperties
) {

    /**
     * 토큰을 생성하여 dto로 감싸 반환하는 메서드 입니다.
     *
     * @param authenticationId 사용자를 식별할 id
     * @return 생성된 토큰을 담고 있는 dto
     */
    fun generateToken(authenticationId: Long): TokenDto =
        TokenDto(
            accessToken = generateAccessToken(authenticationId),
            refreshToken = generateRefreshToken(authenticationId),
            accessTokenExp = jwtExpTimeProperties.accessExp,
            refreshTokenExp = jwtExpTimeProperties.refreshExp
        )

    /**
     * authenticationId로 accessToken을 생성합니다.
     *
     * @param authenticationId 사용자를 식별할 id
     * @return 생성된 accessToken
     */
    private fun generateAccessToken(authenticationId: Long): String =
        Jwts.builder()
            .signWith(jwtProperties.accessSecret, SignatureAlgorithm.HS256)
            .setSubject(authenticationId.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.ACCESS)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.accessExp * 1000))
            .compact()

    /**
     * authenticationId로 refreshToken을 생성합니다.
     *
     * @param authenticationId 사용자를 식별할 id
     * @return 생성된 refreshToken
     */
    private fun generateRefreshToken(authenticationId: Long): String =
        Jwts.builder()
            .signWith(jwtProperties.refreshSecret, SignatureAlgorithm.HS256)
            .setSubject(authenticationId.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.refreshExp * 1000))
            .compact()

}
