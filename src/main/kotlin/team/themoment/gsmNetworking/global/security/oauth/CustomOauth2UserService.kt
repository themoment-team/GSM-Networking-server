package team.themoment.gsmNetworking.global.security.oauth

import org.springframework.beans.factory.annotation.Value
import team.themoment.gsmNetworking.domain.auth.domain.Authentication
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.domain.auth.repository.AuthenticationRepository
import team.themoment.gsmNetworking.global.security.oauth.dto.UserInfo
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import team.themoment.gsmNetworking.global.security.handler.CustomAuthenticationFailureHandler
import java.time.LocalDateTime

@Service
class CustomOauth2UserService(
    private val authenticationRepository: AuthenticationRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Value("\${gsm.teacher.email}")
    private val GSM_TEACHER_EMAIL: String = ""

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
            "sub" to authentication.id,
            "provider_id" to providerId,
            "authority" to authority,
            "last_login_time" to LocalDateTime.now()
        )
        val authorities = mutableListOf(SimpleGrantedAuthority(authority.name))

        return UserInfo(authorities, attributes, userNameAttributeName)
    }

    /**
     * 이메일이 정규식 조건에 부합하는지 확인하는 메서드입니다.
     *
     * @param email 사용자의 이메일
     * @see CustomAuthenticationFailureHandler.oauth2AuthenticationExceptionHandler
     */
    private fun validateEmailDomain(email: String): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@gsm\\.hs\\.kr$")


        if (email == GSM_TEACHER_EMAIL) return email

        if (!regex.matches(email)) {
            throw OAuth2AuthenticationException(
                OAuth2Error(
                    "NOT_STUDENT_ACCOUNT",
                ),
                "요청한 이메일이 GSM 학생용 이메일이 아닙니다."
            )
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
