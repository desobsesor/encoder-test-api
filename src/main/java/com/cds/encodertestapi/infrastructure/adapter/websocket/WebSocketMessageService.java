package com.cds.encodertestapi.infrastructure.adapter.websocket;

import com.cds.encodertestapi.infrastructure.adapter.websocket.dto.LoginNotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service for sending WebSocket messages to clients
 */
@Service
@RequiredArgsConstructor
public class WebSocketMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    private static final String LOGIN_NOTIFICATION_DESTINATION = "/topic/login-notifications";
    private static final String LOGIN_SUCCESS_TYPE = "LOGIN_SUCCESS";

    /**
     * Sends a login success notification via WebSocket
     * 
     * @param username The username of the authenticated user
     */
    public void sendLoginSuccessNotification(String username) {
        LoginNotificationMessage notification = LoginNotificationMessage.builder()
                .username(username)
                .timestamp(LocalDateTime.now())
                .type(LOGIN_SUCCESS_TYPE)
                .message("User successfully authenticated")
                .build();

        messagingTemplate.convertAndSend(LOGIN_NOTIFICATION_DESTINATION, notification);
    }
}