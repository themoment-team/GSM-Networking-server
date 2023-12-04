package team.themoment.gsmNetworking.domain.auth.repository

import team.themoment.gsmNetworking.domain.auth.domain.RefreshToken
import org.springframework.data.repository.CrudRepository

/**
 * RefreshToken Entity를 위한 Repository 인터페이스 입니다.
 */
interface RefreshTokenRepository: CrudRepository<RefreshToken, String> {

    fun findByToken(token: String): RefreshToken?

}
