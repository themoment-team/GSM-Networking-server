package team.themoment.gsmNetworking.global.security.filter.config

import team.themoment.gsmNetworking.global.security.filter.Oauth2ExceptionHandlerFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component

@Component
class FilterConfig: SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(Oauth2ExceptionHandlerFilter(), OAuth2LoginAuthenticationFilter::class.java)
    }

}