package team.themoment.gsmNetworking.domain.community.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gwangya.token")
class GwangyaProperties(
    val tokenExp: Long
)
