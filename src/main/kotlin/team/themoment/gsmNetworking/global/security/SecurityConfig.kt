package team.themoment.gsmNetworking.global.security

import team.themoment.gsmNetworking.global.security.filter.config.FilterConfig
import team.themoment.gsmNetworking.global.security.oauth.CustomOauth2UserService
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOauth2UserService: CustomOauth2UserService,
    private val logoutSuccessHandler: LogoutSuccessHandler,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().and()
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .apply(FilterConfig())
        logout(http)
        oauth2Login(http)
        return http.build()
    }

    private fun oauth2Login(http: HttpSecurity) {
        http
            .oauth2Login()
            .userInfoEndpoint().userService(customOauth2UserService)
            .and()
            .authorizationEndpoint().baseUri(Oauth2Properties.OAUTH2_LOGIN_END_POINT_BASE_URI).and()
            .successHandler(authenticationSuccessHandler)
    }

    private fun logout(http: HttpSecurity) {
        http.logout()
            .logoutUrl(Oauth2Properties.LOGOUT_URI)
            .logoutSuccessHandler(logoutSuccessHandler)
    }

}