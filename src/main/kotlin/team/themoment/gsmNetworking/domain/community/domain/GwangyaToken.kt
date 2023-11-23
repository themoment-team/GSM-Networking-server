package team.themoment.gsmNetworking.domain.community.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("gwangya_token")
class GwangyaToken(
    @Id
    val gwangyaToken: String,

    @TimeToLive
    val expirationTime: Long
)
