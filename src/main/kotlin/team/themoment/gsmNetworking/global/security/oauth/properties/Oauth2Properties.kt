package team.themoment.gsmNetworking.global.security.oauth.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("oauth2.login.success")
class Oauth2Properties(
    val signUpRedirectUrl: String,
    val defaultRedirectUrl: String
) {

    companion object {
        const val EMAIL = "email"
        const val OAUTH2_LOGIN_END_POINT_BASE_URI = "/api/v1/auth/oauth2/authorization"
        const val LOGOUT_URI = "/api/v1/auth/logout"
    }

}
