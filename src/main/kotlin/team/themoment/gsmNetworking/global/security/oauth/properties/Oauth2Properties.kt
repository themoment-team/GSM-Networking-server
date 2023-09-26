package team.themoment.gsmNetworking.global.security.oauth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("spring.security.oauth2.client.registration.google.success")
class Oauth2Properties(
    val redirectUri: String
) {

    companion object {
        const val EMAIL = "email"
        const val OAUTH2_LOGIN_END_POINT_BASE_URI = "/api/v1/auth/oauth2/authorization"
        const val LOGOUT_URI = "/api/v1/auth/logout"
    }

}