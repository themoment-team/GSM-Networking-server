package team.themoment.gsmNetworking.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.domain.RefreshToken
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import team.themoment.gsmNetworking.global.security.jwt.TokenGenerator
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenResponse
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Transactional(rollbackFor = [Exception::class])
class CustomUrlAuthenticationSuccessHandler(
    private val tokenGenerator: TokenGenerator,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtExpTimeProperties: JwtExpTimeProperties,
): AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val authenticationId = authentication.name.toLong()
        val authority = authentication.authorities.iterator().next().authority
        val token = tokenGenerator.generateToken(authenticationId, Authority.valueOf(authority.toString()))
        saveRefreshToken(token.refreshToken, authenticationId)
        sendTokenResponse(response, token)
    }

    private fun saveRefreshToken(token: String, authenticationId: Long) {
        val refreshToken = RefreshToken(
            token = token,
            authenticationId = authenticationId,
            expirationTime = jwtExpTimeProperties.refreshExp
        )
        refreshTokenRepository.save(refreshToken)
    }

    private fun sendTokenResponse(response: HttpServletResponse, token: TokenResponse) {
        response.characterEncoding = "utf-8"
        response.status = HttpServletResponse.SC_OK
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        response.writer.write(objectMapper.writeValueAsString(token))
    }

}