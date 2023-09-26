package team.themoment.gsmNetworking.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 로그아웃 시 실행되는 handler 클래스 입니다.
 */
@Component
class CustomLogoutSuccessHandler(
    private val oauth2Properties: Oauth2Properties,
    private val refreshTokenRepository: RefreshTokenRepository,
): LogoutSuccessHandler {

    /**
     * 로그아웃 성공 후 Redis에 저장되어있던 재발급 토큰을 삭제 시킵니다.
     */
    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        response.status = HttpServletResponse.SC_OK
        if (authentication != null) {
            val refreshToken = refreshTokenRepository.findByIdOrNull(authentication.name)
                ?: throw ExpectedException("존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND)
            refreshTokenRepository.delete(refreshToken)
        } else sendErrorResponse(response)
        response.sendRedirect(oauth2Properties.redirectUri)
    }

    private fun sendErrorResponse(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.characterEncoding = "utf-8"
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val map = mapOf("message" to "이미 로그아웃 된 사용자 입니다.")
        response.writer.write(ObjectMapper().writeValueAsString(map))
    }

}