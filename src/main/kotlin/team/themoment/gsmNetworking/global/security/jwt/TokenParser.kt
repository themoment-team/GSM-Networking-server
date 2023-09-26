package team.themoment.gsmNetworking.global.security.jwt

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties

/**
 * 토큰을 파싱하는 클래스 입니다.
 */
@Component
class TokenParser {

    /**
     * 재발급 토큰의 prefix를 파싱하는 역할의 메서드 입니다.
     *
     * @param refreshToken prefix를 포함한 refreshToken 입니다.
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
    *       1. jwt의 토큰 prefix인 Bearer이 붙지 않은 경우
     * @return prefix를 replace한 refreshToken을 반환 합니다.
     */
    fun parseRefreshToken(refreshToken: String): String {
        if (refreshToken.startsWith(JwtProperties.TOKEN_PREFIX))
            return refreshToken.replace(JwtProperties.TOKEN_PREFIX, "")
        throw ExpectedException("유효하지 않은 refresh token 입니다.", HttpStatus.UNAUTHORIZED)
    }

}