package team.themoment.gsmNetworking.domain.auth.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

/**
 * 재발급 토큰 정보를 저장하는 RedisHash 클래스 입니다.
 */
@RedisHash("refresh_token")
data class RefreshToken(
    @Id
    val email: String,

    @Indexed
    val token: String,

    @TimeToLive
    val expirationTime: Int
)