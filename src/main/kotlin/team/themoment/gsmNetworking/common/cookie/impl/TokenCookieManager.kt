package team.themoment.gsmNetworking.common.cookie.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.cookie.CookieManager
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * CookieManager의 토큰 관련 구현체입니다.
 */
@Component
class TokenCookieManager : CookieManager {

    @Value("\${server.cookie.domain}")
    private val GSM_NETWORKING_DOMAIN: String = ""

    @Value("\${server.cookie.secure}")
    private val SECURE: Boolean = false

    /**
     * 토큰을 쿠키로 만들어주어 쿠키를 담는 메서드 입니다.
     */
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

    /**
     * 쿠키를 반환해주는 메서드 입니다.
     *
     * @return name으로 찾은 쿠키
     */
    override fun getCookieValueOrNull(cookies: Array<Cookie>, name: String): String? {
        var value: String? = null
        if (cookies.isNotEmpty()) {
            cookies.forEach {
                if (it.name == name) value = it.value
            }
        }
        return value
    }

    /**
     * 쿠키를 생성해주는 메서드 입니다.
     *
     * @return 생성된 쿠키를 반환합니다.
     */
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
