package team.themoment.gsmNetworking.global.security.handler

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.global.filter.LoggingFilter
import team.themoment.gsmNetworking.global.security.oauth.CustomOauth2UserService
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = LoggerFactory.getLogger(LoggingFilter::class.java)!!

@Component
class CustomAuthenticationFailureHandler(
    @Value("\${gsm.student.account.otherwise-redirect}")
    private val redirectUriForNonGSMStudentAccount: String,
) : AuthenticationFailureHandler {

    /**
     * 인증 과정에서 발생한 예외를 핸들링합니다.
     *
     * @param request 요청 받은 servletRequest
     * @param response 응답할 servletResponse
     * @param exception 인증 과정에서 발생한 예외
     */
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) {
        if (exception is OAuth2AuthenticationException) {
            oauth2AuthenticationExceptionHandler(exception, response)
        }
    }

    /**
     * oauth2AuthenticationException을 핸들링합니다.
     *
     * @param exception oauth2 인증 과정에서 발생한 예외
     * @param response 응답할 servletResponse
     *
     * @see CustomOauth2UserService.validateEmailDomain
     */
    private fun oauth2AuthenticationExceptionHandler(
        exception: OAuth2AuthenticationException,
        response: HttpServletResponse,
    ) {
        if (exception.error.errorCode == "NOT_STUDENT_ACCOUNT") {
            log.warn("Oauth2AuthenticationException message: ${exception.message}")
            response.sendRedirect(redirectUriForNonGSMStudentAccount)
        }
    }
}
