package team.themoment.gsmNetworking.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
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
     * authentication 객체가 있다면 redis에 저장되어있던 재발급 토큰을 삭제 시킵니다.
     *
     * @param request 요청 받은 servletRequest
     * @param response 응답할 servletResponse
     * @param authentication 생성된 인증 객체
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
     *      1. RefreshToken Entity에 저장되지 않은 RefreshToken일 경우
     */
    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        if (authentication != null) {
            val refreshToken = refreshTokenRepository.findByIdOrNull(authentication.name)
                ?: throw ExpectedException("존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND)
            refreshTokenRepository.delete(refreshToken)
            response.status = HttpServletResponse.SC_RESET_CONTENT
            response.sendRedirect(oauth2Properties.defaultRedirectUrl)
        }
        sendErrorResponse(response)
    }

    /**
     * authentication 객체가 존재하지 않을 경우 에러 메세지를 반환하는 메서드 입니다.
     *
     * @param response 응답할 serveltResponse
     */
    private fun sendErrorResponse(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val map = mapOf("message" to "이미 로그아웃 된 사용자 입니다.")
        response.writer.write(ObjectMapper().writeValueAsString(map))
    }

}
