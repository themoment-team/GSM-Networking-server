package team.themoment.gsmNetworking.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcCorsConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000", "https://localhost:3000",
                "https://gsm.moip.shop", "https://server.gsm.moip.shop"
            )
            .allowedMethods(*allowedHttpMethod())
            .allowCredentials(true)
            .maxAge(3000)
    }

    private fun allowedHttpMethod(): Array<out String> =
        arrayOf(
            HttpMethod.GET.name,
            HttpMethod.POST.name,
            HttpMethod.PATCH.name,
            HttpMethod.PUT.name,
            HttpMethod.DELETE.name,
            HttpMethod.HEAD.name,
            HttpMethod.OPTIONS.name,
        )

}