package team.themoment.gsmNetworking.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import team.themoment.gsmNetworking.global.socket.handler.StompErrorHandler
import team.themoment.gsmNetworking.global.socket.interceptor.StompInterceptor

@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig(
    private val stompInterceptor: StompInterceptor
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/queue", "/topic")
        config.setApplicationDestinationPrefixes("/pub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*") // TODO 보안 설정 나중에 추가하기
        registry.setErrorHandler(StompErrorHandler())
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration
            .interceptors(stompInterceptor)
    }
}
