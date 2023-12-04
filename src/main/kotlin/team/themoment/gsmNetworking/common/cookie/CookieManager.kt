package team.themoment.gsmNetworking.common.cookie

import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

interface CookieManager {

    fun addTokenCookie(tokenDto: TokenDto, response: HttpServletResponse)

    fun getCookieValueOrNull(cookies: Array<Cookie>, name: String): String?

}
