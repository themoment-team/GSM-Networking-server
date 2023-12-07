package team.themoment.gsmNetworking.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomLogoutHandler(
    private val refreshTokenRepository: RefreshTokenRepository,
) : LogoutHandler {

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        if (authentication != null) {
            val refreshToken = refreshTokenRepository.findByIdOrNull(authentication.name)
                ?: throw ExpectedException("존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND)
            refreshTokenRepository.delete(refreshToken)
        } else {
            sendErrorResponse(response)
        }
    }

    private fun sendErrorResponse(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val map = mapOf("message" to "이미 로그아웃 된 사용자 입니다.")
        response.writer.write(ObjectMapper().writeValueAsString(map))
    }
}