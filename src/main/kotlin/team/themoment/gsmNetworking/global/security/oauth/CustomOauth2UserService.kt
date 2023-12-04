package team.themoment.gsmNetworking.global.security.oauth

import team.themoment.gsmNetworking.domain.auth.domain.Authentication
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.global.security.oauth.dto.UserInfo
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.common.exception.ExpectedException
import java.time.LocalDateTime

@Service
class CustomOauth2UserService(
    private val authenticationRepository: AuthenticationRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegateOauth2UserService = DefaultOAuth2UserService()
        val oauth2User = delegateOauth2UserService.loadUser(userRequest)

        val email = validateEmailDomain(oauth2User.attributes[Oauth2Properties.EMAIL].toString())
        val providerId = oauth2User.name
        val authentication = getAuthenticationIsNullSave(email, providerId)
        val authority = authentication.authority
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails.userInfoEndpoint.userNameAttributeName
        val attributes = mapOf(
            "sub" to authentication.authenticationId,
            "provider_id" to providerId,
            "authority" to authority,
            "last_login_time" to LocalDateTime.now()
        )
        val authorities = mutableListOf(SimpleGrantedAuthority(authority.name))

        return UserInfo(authorities, attributes, userNameAttributeName)
    }

    private fun validateEmailDomain(email: String): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@gsm\\.hs\\.kr$")
        if (!regex.matches(email)) {
            throw ExpectedException("요청한 이메일이 GSM 학생용 이메일이 아닙니다.", HttpStatus.BAD_REQUEST)
        }
        return email
    }

    private fun getAuthenticationIsNullSave(email: String, providerId: String): Authentication =
        authenticationRepository.findByEmail(email) ?: saveAuthentication(email, providerId)

    private fun saveAuthentication(email: String, providerId: String): Authentication {
        val authentication = Authentication(
            email = email,
            providerId = providerId,
            authority = Authority.UNAUTHENTICATED
        )
        return authenticationRepository.save(authentication)
    }

}
