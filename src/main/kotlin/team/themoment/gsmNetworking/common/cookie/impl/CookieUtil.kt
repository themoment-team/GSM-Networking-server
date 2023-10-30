package team.themoment.gsmNetworking.common.cookie.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.cookie.CookieManager
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * 쿠키에 관한 로직이 담겨있는 util 클래스 입니다.
 *
 */
@Component
class CookieUtil : CookieManager {

    @Value("\${server.cookie.domain}")
    private val GSM_NETWORKING_DOMAIN: String = ""

    @Value("\${server.cookie.secure}")
    private val SECURE: Boolean = false

    override fun addTokenCookie(tokenDto: TokenDto, response: HttpServletResponse) {
        val accessTokenCookie = addCookie(
            name = JwtProperties.ACCESS,
            value = tokenDto.accessToken,
            maxAge = tokenDto.accessTokenExp
        )
        val refreshTokenCookie = addCookie(
            name = JwtProperties.REFRESH,
            value = tokenDto.refreshToken,
            maxAge = tokenDto.refreshTokenExp
        )
        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
    }

    override fun getCookieValueOrNull(cookies: Array<Cookie>, name: String): String? {
        var value: String? = null
        if (cookies.isNotEmpty()) {
            cookies.forEach {
                if (it.name == name) value = it.value
            }
        }
        return value
    }

    private fun addCookie(name: String, value: String, maxAge: Int): Cookie {
        val cookie = Cookie(name, value)
        cookie.maxAge = maxAge
        cookie.domain = GSM_NETWORKING_DOMAIN
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.secure = SECURE
        return cookie
    }

}
