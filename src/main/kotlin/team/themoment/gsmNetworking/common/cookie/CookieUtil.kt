package team.themoment.gsmNetworking.common.cookie

import org.springframework.beans.factory.annotation.Value
import team.themoment.gsmNetworking.common.exception.ExpectedException
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

        /**
         * 토큰을 쿠키로 만들어주는 메서드 입니다.
         */
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

        /**
         * 쿠키의 name으로 쿠키를 반환해주는 메서드 입니다.
         * @throws ExpectedException 이 터지는 조건은 아래와 같다.
         *      1.
         * @return name으로 찾은 쿠키
         */
        fun getCookieValueOrNull(cookies: Array<Cookie>, name: String): String? {
            var value: String? = null
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
