package com.cds.encodertestapi.infrastructure.adapter.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Listener for authentication success events to trigger WebSocket notifications
 * This ensures notifications are sent regardless of how authentication occurs
 */
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final WebSocketMessageService webSocketMessageService;

    /**
     * Handle authentication success events
     * 
     * @param event The authentication success event
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // Extract the username from the authentication object
        Object principal = event.getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Send WebSocket notification
        webSocketMessageService.sendLoginSuccessNotification(username);
    }
}