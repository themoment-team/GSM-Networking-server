package team.themoment.gsmNetworking.common.cookie

import org.springframework.beans.factory.annotation.Value
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * 쿠키에 관한 로직이 담겨있는 util 클래스 입니다.
 *
 */
class CookieUtil {

    companion object {

        @Value("gsm-networking.domain")
        const val gsmNetworkingDomain: String = ""

        fun addTokenCookie(tokenDto: TokenDto, response: HttpServletResponse) {
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

        fun getCookieValueOrNull(cookies: Array<Cookie>, name: String): String? {
            var value: String? = ""
            if (cookies.isNotEmpty()) {
                cookies.forEach {
                    if (it.name.equals(name)) value = it.value
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
            cookie.domain = gsmNetworkingDomain
            cookie.path = "/"
            cookie.isHttpOnly = true
            return cookie
        }

    }

}