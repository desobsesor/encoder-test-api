package com.cds.encodertestapi.infrastructure.adapter.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for login notification messages sent via WebSocket
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login notification message")
public class LoginNotificationMessage {

    @Schema(description = "Username of the logged-in user", example = "user123")
    private String username;

    @Schema(description = "Timestamp of the login event", example = "2023-12-01T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "Type of notification", example = "LOGIN_SUCCESS")
    private String type;

    @Schema(description = "Additional message details", example = "User successfully authenticated")
    private String message;
}