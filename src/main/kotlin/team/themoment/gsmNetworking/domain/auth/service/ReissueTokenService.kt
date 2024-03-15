package team.themoment.gsmNetworking.domain.auth.service

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.gsmNetworking.common.exception.ExpectedException
import team.themoment.gsmNetworking.domain.auth.domain.RefreshToken
import team.themoment.gsmNetworking.domain.auth.repository.RefreshTokenRepository
import team.themoment.gsmNetworking.global.security.jwt.TokenGenerator
import team.themoment.gsmNetworking.global.security.jwt.dto.TokenDto
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtExpTimeProperties

/**
 * 로그인 연장을 위한 토큰 재발급을 해주는 서비스 클래스 입니다.
 */
@Service
class ReissueTokenService(
    private val tokenGenerator: TokenGenerator,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtExpTimeProperties: JwtExpTimeProperties
) {

    /**
     * 유효한 RefreshToken 이라면 토큰을 반환하고, 유효하지 않은 RefreshToken 이라면 예외를 던집니다.
     *
     * @param token 로그인 연장에 필요한 재발급(RefreshToken) 토큰
     * @throws ExpectedException 이 터지는 조건은 아래와 같다.
     *      1. RefreshToken Entity에 저장되지 않은 RefreshToken일 경우
     *      2. RefreshToken의 Subject가 저장되지 않은 경우
     *      3. 유효하지 않은 Token Prefix로 요청 한 경우
     * @return 재발급된 토큰을 담고 있는 dto
     */
    @Transactional
    fun execute(token: String?): TokenDto {
        if (token == null) throw ExpectedException("refresh token 쿠키를 가지고 있지 않은 사용자 입니다.", HttpStatus.NOT_FOUND)
        val refreshToken = refreshTokenRepository.findByToken(token)
            ?: throw ExpectedException("존재하지 않는 refresh token 입니다.", HttpStatus.NOT_FOUND)
        val tokenDto = tokenGenerator.generateToken(refreshToken.authenticationId)
        saveRefreshToken(tokenDto.refreshToken, refreshToken.authenticationId)
        return tokenDto
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
