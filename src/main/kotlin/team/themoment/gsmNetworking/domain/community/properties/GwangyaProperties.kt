package team.themoment.gsmNetworking.domain.community.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gwangya.time")
class GwangyaProperties(
    val tokenExp: Long
)
