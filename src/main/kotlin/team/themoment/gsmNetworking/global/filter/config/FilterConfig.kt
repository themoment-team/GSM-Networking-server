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
     * 1. LoggingFilter <- 로깅을 위한 필터
     * 2. ExceptionHandlerFilter <- 커스텀 필터들의 앞에 오지않으면 필터단에서 발생한 예외를 핸들링하지 못함
     * 3. TokenRequestFilter <- 추출한 토큰을 이용해 사용자의 authentication 정보를 설정함
     * 4. LogoutFilter <- 커스텀 필터는 아니지만 TokenRequestFilter 뒤에 와야 logout시 authentication 객체가 생성이 되서 옴
     */
    override fun configure(builder: HttpSecurity) {
        //TODO 필터 순서가 명확하게 나타나도록 코드 리펙터링이 필요함
        builder.addFilterAfter(exceptionHandlerFilter, CorsFilter::class.java)
        builder.addFilterAfter(tokenRequestFilter, CorsFilter::class.java)
        builder.addFilterBefore(loggingFilter, ExceptionHandlerFilter::class.java)
    }

}
