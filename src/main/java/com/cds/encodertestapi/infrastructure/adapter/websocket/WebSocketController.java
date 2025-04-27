package com.cds.encodertestapi.infrastructure.adapter.websocket;

import com.cds.encodertestapi.infrastructure.adapter.websocket.dto.LoginNotificationMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Controller for handling WebSocket messages
 */
@Controller
@RequiredArgsConstructor
@Tag(name = "WebSocket", description = "WebSocket endpoints for real-time notifications")
public class WebSocketController {

    /**
     * Echo endpoint for testing WebSocket connectivity
     * 
     * @param message The message to echo back
     * @return The same message
     */
    @MessageMapping("/echo")
    @SendTo("/topic/echo")
    @Operation(summary = "Echo message", description = "Echoes back the received message for testing WebSocket connectivity")
    public LoginNotificationMessage echo(LoginNotificationMessage message) {
        return message;
    }
}