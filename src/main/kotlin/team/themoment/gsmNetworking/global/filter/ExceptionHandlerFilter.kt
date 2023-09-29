package team.themoment.gsmNetworking.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import team.themoment.gsmNetworking.common.exception.model.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import team.themoment.gsmNetworking.common.exception.ExpectedException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExceptionHandlerFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            when(exception) {
                is ExpiredJwtException -> exceptionToResponse("만료된 jwt 토큰 입니다.", HttpStatus.UNAUTHORIZED, response)
                is JwtException -> exceptionToResponse("유효하지 않은 jwt 토큰 입니다.", HttpStatus.UNAUTHORIZED, response)
                is ExpectedException -> exceptionToResponse(exception.message, exception.status, response)
            }
        }
    }

    private fun exceptionToResponse(message: String, status: HttpStatus, response: HttpServletResponse) {
        response.status = status.value()
        response.contentType = "application/json"
        response.characterEncoding = "utf-8"
        val errorResponse = ExceptionResponse(message)
        val errorResponseToJson = ObjectMapper().writeValueAsString(errorResponse)
        response.writer.write(errorResponseToJson)
    }

}