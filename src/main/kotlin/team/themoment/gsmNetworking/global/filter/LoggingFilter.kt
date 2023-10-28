package team.themoment.gsmNetworking.global.filter

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val log = LoggerFactory.getLogger(LoggingFilter::class.java)!!

@Component
class LoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val startTime = System.currentTimeMillis()
        val logId = UUID.randomUUID()
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)

        runCatching {
            filterChain.doFilter(requestWrapper, responseWrapper)
        }.onSuccess {
            requestLogging(request = requestWrapper, logId = logId)
            responseLogging(response = responseWrapper, startTime = startTime, logId = logId)
            responseWrapper.copyBodyToResponse()
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun requestLogging(request: ContentCachingRequestWrapper, logId: UUID) {
        log.info("Log-ID: $logId, IP: ${request.remoteAddr}, URI: ${request.requestURI}, Http Method: ${request.method}, Params: ${request.queryString}, Content-Type: ${request.contentType}, User-Cookies: ${request.cookies.joinToString(", ") { "${it.name}=${it.value}" }}, Body: ${getRequestBody(request.inputStream)}")
    }

    private fun responseLogging(response: ContentCachingResponseWrapper, startTime: Long, logId: UUID) {
        val endTime = System.currentTimeMillis()
        val responseTime = endTime - startTime
        log.info("Log-ID: $logId, Status: ${response.status}, Content-Type: ${response.contentType}, Response Time: ${responseTime}ms, Body: ${String(response.contentAsByteArray)}")
    }

    private fun getRequestBody(inputStream: InputStream): String {
        val body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
        return if (StringUtils.hasText(body)) body else "[empty]"
    }

}
