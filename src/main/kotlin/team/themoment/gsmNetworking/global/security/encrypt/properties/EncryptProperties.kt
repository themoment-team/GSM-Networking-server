package team.themoment.gsmNetworking.global.security.encrypt.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("encrypt")
class EncryptProperties(
    val secretKey: String
)
