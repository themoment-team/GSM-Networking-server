package team.themoment.gsmNetworking.global.security.handler

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomLogoutHandler(
    private val oauth2Properties: Oauth2Properties,
    private val refreshTokenRepository: RefreshTokenRepository,
) : LogoutHandler {

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        if (authentication != null) {
            val refreshToken = refreshTokenRepository.findByIdOrNull(authentication.name)
                ?: throw ExpectedException("${authentication.name}로/으로 refreshToken을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
            refreshTokenRepository.delete(refreshToken)
        } else {
            response.sendRedirect(oauth2Properties.defaultRedirectUrl)
            throw ExpectedException("이미 로그아웃 된 사용자입니다.", HttpStatus.BAD_REQUEST)
        }
    }
}
