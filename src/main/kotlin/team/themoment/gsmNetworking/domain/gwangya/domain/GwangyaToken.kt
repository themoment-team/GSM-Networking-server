package team.themoment.gsmNetworking.domain.gwangya.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash("gwangya_token")
data class GwangyaToken(
    @Id
    val id: String,

    @Indexed
    val gwangyaToken: String,

    val createdAt: LocalDateTime,

    @TimeToLive
    val expirationTime: Long
)
