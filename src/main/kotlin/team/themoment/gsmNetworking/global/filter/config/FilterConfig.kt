package team.themoment.gsmNetworking.global.filter.config

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.filter.CorsFilter
import team.themoment.gsmNetworking.global.filter.ExceptionHandlerFilter
import team.themoment.gsmNetworking.global.filter.LoggingFilter
import team.themoment.gsmNetworking.global.filter.TokenRequestFilter

@Component
class FilterConfig(
    private val tokenRequestFilter: TokenRequestFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter,
    private val loggingFilter: LoggingFilter,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    /**
     * 커스텀 필터 순서
     * 1. LoggingFilter
     * 2. ExceptionHandlerFilter
     * 3. TokenRequestFilter
     * 4. LogoutFilter <- 커스텀 필터는 아니지만 TokenRequestFilter 뒤에 와야함
     */
    override fun configure(builder: HttpSecurity) {
        //TODO 나중에 커스텀 필터를 추가하게 된다면 코드 수정이 필요함
        builder.addFilterAfter(exceptionHandlerFilter, CorsFilter::class.java)
        builder.addFilterAfter(tokenRequestFilter, CorsFilter::class.java)
        builder.addFilterBefore(loggingFilter, ExceptionHandlerFilter::class.java)
    }

}
