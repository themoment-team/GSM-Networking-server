package team.themoment.gsmNetworking.domain.gwangya.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("gwangya.token")
class GwangyaProperties(
    val tokenExp: Long,
)
