package com.cds.encodertestapi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket configuration for real-time notifications
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configure message broker for WebSocket communication
     * 
     * @param registry MessageBrokerRegistry to configure
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Enable a simple memory-based message broker to send messages to clients
        // on destinations prefixed with /topic
        registry.enableSimpleBroker("/topic");

        // Set prefix for messages bound for @MessageMapping methods
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Register STOMP endpoints for WebSocket connections
     * 
     * @param registry StompEndpointRegistry to configure
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the "/ws" endpoint, enabling SockJS fallback options for browsers
        // that don't support WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Configure CORS for WebSocket
                .withSockJS();
    }
}