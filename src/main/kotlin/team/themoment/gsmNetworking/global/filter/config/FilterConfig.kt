package team.themoment.gsmNetworking.global.filter.config

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import team.themoment.gsmNetworking.global.filter.ExceptionHandlerFilter
import team.themoment.gsmNetworking.global.filter.LoggingFilter
import team.themoment.gsmNetworking.global.filter.TokenRequestFilter

@Component
class FilterConfig(
    private val tokenRequestFilter: TokenRequestFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val loggingFilter: LoggingFilter,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(tokenRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionHandlerFilter, OAuth2LoginAuthenticationFilter::class.java)
        builder.addFilterBefore(exceptionHandlerFilter, TokenRequestFilter::class.java)
        builder.addFilterBefore(loggingFilter, ExceptionHandlerFilter::class.java)
    }

}
