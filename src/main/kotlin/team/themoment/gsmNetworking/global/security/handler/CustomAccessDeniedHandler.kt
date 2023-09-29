package team.themoment.gsmNetworking.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAccessDeniedHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        sendErrorResponse(response)
    }

    private fun sendErrorResponse(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        val message = mapOf("message" to "요청을 수행할 수 있는 권한을 가진 사용자가 아닙니다.")
        response.writer.write(ObjectMapper().writeValueAsString(message))
    }

}