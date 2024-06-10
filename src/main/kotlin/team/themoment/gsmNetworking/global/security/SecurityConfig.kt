package team.themoment.gsmNetworking.global.security

import team.themoment.gsmNetworking.global.security.oauth.CustomOauth2UserService
import team.themoment.gsmNetworking.global.security.oauth.properties.Oauth2Properties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import team.themoment.gsmNetworking.domain.auth.domain.Authority
import team.themoment.gsmNetworking.global.filter.ExceptionHandlerFilter
import team.themoment.gsmNetworking.global.filter.LoggingFilter
import team.themoment.gsmNetworking.global.filter.TokenRequestFilter
import team.themoment.gsmNetworking.global.filter.config.FilterConfig
import team.themoment.gsmNetworking.global.security.handler.CustomAccessDeniedHandler
import team.themoment.gsmNetworking.global.security.handler.CustomAuthenticationEntryPoint
import team.themoment.gsmNetworking.global.security.jwt.properties.JwtProperties

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOauth2UserService: CustomOauth2UserService,
    private val logoutHandler: LogoutHandler,
    private val logoutSuccessHandler: LogoutSuccessHandler,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val authenticationFailureHandler: AuthenticationFailureHandler,
    private val tokenRequestFilter: TokenRequestFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val loggingFilter: LoggingFilter,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().and()
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .apply(FilterConfig(tokenRequestFilter, exceptionHandlerFilter, loggingFilter))
        logout(http)
        oauth2Login(http)
        authorizeHttpRequests(http)
        exceptionHandling(http)
        return http.build()
    }

    private fun authorizeHttpRequests(http: HttpSecurity) {
        http.authorizeHttpRequests()
            // /mentor
            .mvcMatchers("/api/v1/mentor/**").hasAnyRole(
                Authority.USER.name,
                Authority.TEACHER.name
            )
            .mvcMatchers(HttpMethod.GET, "/api/v1/mentor").hasAnyRole(
                Authority.TEMP_USER.name,
                Authority.USER.name,
                Authority.ADMIN.name,
                Authority.TEACHER.name
            )
            .mvcMatchers(HttpMethod.POST, "/api/v1/mentor").hasAnyRole(
                Authority.UNAUTHENTICATED.name,
                Authority.TEMP_USER.name
            )
            .mvcMatchers("/api/v1/mentor/my").hasAnyRole(
                Authority.USER.name,
                Authority.TEACHER.name
            )
            // /tempMentor
            .mvcMatchers(HttpMethod.GET, "api/v1/temp-mentor/search/*").hasAnyRole(
                Authority.UNAUTHENTICATED.name,
                Authority.TEMP_USER.name,
                Authority.ADMIN.name
            )
            .mvcMatchers(HttpMethod.GET, "/api/v1/temp-mentor/**").hasAnyRole(
                Authority.UNAUTHENTICATED.name,
                Authority.TEMP_USER.name,
                Authority.ADMIN.name
            )
            .mvcMatchers(HttpMethod.DELETE, "/api/v1/temp-mentor/*").hasAnyRole(
                Authority.USER.name
            )
            // /mentee
            .mvcMatchers(HttpMethod.PATCH, "/api/v1/mentee/update").hasAnyRole(
                Authority.UNAUTHENTICATED.name
            )
            .mvcMatchers(HttpMethod.POST, "/api/v1/mentee").hasAnyRole(
                Authority.TEMP_USER.name
            )
            .mvcMatchers(HttpMethod.GET, "/api/v1/mentee/my").hasAnyRole(
                Authority.TEMP_USER.name
            )
            .mvcMatchers("api/v1/mentee/my").hasAnyRole(
                Authority.TEMP_USER.name
            )
            .mvcMatchers("/api/v1/mentee/*").hasAnyRole(
                Authority.USER.name,
                Authority.TEMP_USER.name
            )
            // /user/is-teacher
            .mvcMatchers("/api/v1/user/is-teacher").hasAnyRole(
                Authority.TEACHER.name,
                Authority.TEMP_USER.name,
                Authority.USER.name
            )
            // /user/profile-url
            .mvcMatchers("/api/v1/user/profile-url").hasAnyRole(
                Authority.TEMP_USER.name,
                Authority.USER.name,
                Authority.TEACHER.name
            )
            // /user/profile-number
            .mvcMatchers("/api/v1/user/profile-number").hasAnyRole(
                Authority.TEMP_USER.name,
                Authority.USER.name,
                Authority.TEACHER.name
            )
            // /user
            .mvcMatchers("/api/v1/user/**").hasAnyRole(
                Authority.USER.name,
                Authority.TEACHER.name
            )
            // /file
            .mvcMatchers("/api/v1/file").hasAnyRole(
                Authority.USER.name,
                Authority.TEMP_USER.name,
                Authority.TEACHER.name
            )
            // board
            .mvcMatchers("/api/v1/board/*").hasAnyRole(
                Authority.TEMP_USER.name,
                Authority.USER.name,
                Authority.ADMIN.name,
                Authority.TEACHER.name
            )
            // pin
            .mvcMatchers(HttpMethod.PATCH, "/api/v1/board/pin/*").hasAnyRole(
                Authority.TEACHER.name
            )
            // /gwangya
            .mvcMatchers(HttpMethod.GET, "/api/v1/gwangya/token").hasAnyRole(
                Authority.UNAUTHENTICATED.name,
                Authority.TEMP_USER.name,
                Authority.USER.name,
                Authority.ADMIN.name,
                Authority.TEACHER.name,
            )
            .mvcMatchers(HttpMethod.DELETE, "/api/v1/gwangya/*").hasAnyRole(
                Authority.ADMIN.name
            )
            .anyRequest().permitAll()
    }

    private fun exceptionHandling(http: HttpSecurity) {
        http.exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            .accessDeniedHandler(CustomAccessDeniedHandler())
    }

    private fun oauth2Login(http: HttpSecurity) {
        http
            .oauth2Login()
            .userInfoEndpoint().userService(customOauth2UserService)
            .and()
            .authorizationEndpoint().baseUri(Oauth2Properties.OAUTH2_LOGIN_END_POINT_BASE_URI).and()
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
    }

    private fun logout(http: HttpSecurity) {
        http.logout()
            .logoutUrl(Oauth2Properties.LOGOUT_URI)
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler(logoutSuccessHandler)
            .deleteCookies(JwtProperties.ACCESS, JwtProperties.REFRESH)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        val source = UrlBasedCorsConfigurationSource()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.setAllowedMethods(
            mutableListOf("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTIONS")
        )
        configuration.allowedHeaders = mutableListOf("*")
        configuration.allowCredentials = true
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
