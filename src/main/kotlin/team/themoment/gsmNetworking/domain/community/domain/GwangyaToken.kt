package team.themoment.gsmNetworking.domain.community.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDateTime

@RedisHash("gwangya_token")
class GwangyaToken(
    @Id
    val gwangyaToken: String,

    @Indexed
    val generationTime: LocalDateTime,

    @TimeToLive
    val expirationTime: Long
)