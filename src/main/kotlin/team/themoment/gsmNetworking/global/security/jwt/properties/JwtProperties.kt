package team.themoment.gsmNetworking.global.security.jwt.properties

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.nio.charset.StandardCharsets
import java.security.Key

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    accessSecret: String,
    refreshSecret: String
) {

    val accessSecret: Key = Keys.hmacShaKeyFor(accessSecret.toByteArray(StandardCharsets.UTF_8))
    val refreshSecret: Key = Keys.hmacShaKeyFor(refreshSecret.toByteArray(StandardCharsets.UTF_8))

    companion object {
        const val ACCESS = "accessToken"
        const val REFRESH = "refreshToken"
        const val TOKEN_TYPE = "tokenType"
    }

}
