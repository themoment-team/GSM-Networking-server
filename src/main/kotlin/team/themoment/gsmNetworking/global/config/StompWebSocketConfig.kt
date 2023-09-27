package team.themoment.gsmNetworking.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/sub", "/user")
        config.setApplicationDestinationPrefixes("/pub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
    }
}