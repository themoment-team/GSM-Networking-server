package team.themoment.gsmNetworking.domain.auth.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.cookie.CookieUtil
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.RefreshToken
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import team.themoment.gsmNetworking.global.security.jwt.TokenGenerator
import team.themoment.gsmNetworking.global.security.jwt.TokenParser
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties
import javax.servlet.http.HttpServletResponse

/**
 * 로그인 연장을 위한 토큰 재발급을 해주는 서비스 클래스 입니다.
 */
@Service
class ReissueTokenService(
    private val tokenParser: TokenParser,
    private val tokenGenerator: TokenGenerator,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val jwtExpTimeProperties: JwtExpTimeProperties
) {

    /**
     * 유효한 RefreshToken 이라면 토큰을 쿠키에 저장하고, 유효하지 않은 RefreshToken 이라면 예외를 던집니다.
     *
     * @param token 로그인 연장에 필요한 재발급(RefreshToken) 토큰
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
     *      1. RefreshToken Entity에 저장되지 않은 RefreshToken일 경우
     *      2. RefreshToken의 Subject가 저장되지 않은 경우
     *      3. 유효하지 않은 Token Prefix로 요청 한 경우
     */
    @Transactional(rollbackFor = [Exception::class])
    fun execute(token: String, response: HttpServletResponse) {
        val parsedToken = tokenParser.parseRefreshToken(token)
        val refreshToken = refreshTokenRepository.findByToken(parsedToken)
            ?: throw ExpectedException("존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND)
        val authentication = authenticationRepository.findByIdOrNull(refreshToken.authenticationId)
            ?: throw ExpectedException("refresh token subject인 email을 찾을 수 없습니다.", HttpStatus.NOT_FOUND)
        val tokenDto = tokenGenerator.generateToken(refreshToken.authenticationId)
        CookieUtil.addTokenCookie(tokenDto, response)
        saveRefreshToken(tokenDto.refreshToken, authentication.authenticationId)
    }

    /**
     * refresh token을 redis에 저장합니다.
     *
     * @param token 저장할 refresh token
     * @param authenticationId 사용자를 식별할 id
     */
    private fun saveRefreshToken(token: String, authenticationId: Long) {
        val refreshToken = RefreshToken(
            token = token,
            authenticationId = authenticationId,
            expirationTime = jwtExpTimeProperties.refreshExp
        )
        refreshTokenRepository.save(refreshToken)
    }

}