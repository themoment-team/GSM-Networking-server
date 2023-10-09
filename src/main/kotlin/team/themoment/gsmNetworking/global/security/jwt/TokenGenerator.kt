package team.themoment.gsmNetworking.global.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenResponse
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import java.time.LocalDateTime
import java.util.*

@Component
class TokenGenerator(
    private val jwtProperties: JwtProperties,
    private val jwtExpTimeProperties: JwtExpTimeProperties
) {

    fun generateToken(authenticationId: Long): TokenResponse =
        TokenResponse(
            accessToken = generateAccessToken(authenticationId),
            refreshToken = generateRefreshToken(authenticationId),
            accessTokenExp = LocalDateTime.now().plusSeconds(jwtExpTimeProperties.accessExp.toLong()).withNano(0),
            refreshTokenExp = LocalDateTime.now().plusSeconds(jwtExpTimeProperties.refreshExp.toLong()).withNano(0),
        )

    private fun generateAccessToken(authenticationId: Long): String =
        Jwts.builder()
            .signWith(jwtProperties.accessSecret, SignatureAlgorithm.HS256)
            .setSubject(authenticationId.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.ACCESS)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.accessExp * 1000))
            .compact()

    private fun generateRefreshToken(authenticationId: Long): String =
        Jwts.builder()
            .signWith(jwtProperties.refreshSecret, SignatureAlgorithm.HS256)
            .setSubject(authenticationId.toString())
            .claim(JwtProperties.TOKEN_TYPE, JwtProperties.REFRESH)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpTimeProperties.refreshExp * 1000))
            .compact()

}