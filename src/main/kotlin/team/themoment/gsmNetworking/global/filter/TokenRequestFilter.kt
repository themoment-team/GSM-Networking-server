package team.themoment.gsmNetworking.global.filter

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import team.themoment.gsmNetworking.common.cookie.CookieUtil
import team.themoment.gsmNetworking.global.security.jwt.TokenParser
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenRequestFilter(
    private val tokenParser: TokenParser
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.cookies != null){
            val accessToken = CookieUtil.getCookieValueOrNull(cookies = request.cookies, name = JwtProperties.ACCESS)

            if (!accessToken.isNullOrEmpty()) {
                val authentication = tokenParser.authentication(accessToken)
                SecurityContextHolder.clearContext()
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }

}
