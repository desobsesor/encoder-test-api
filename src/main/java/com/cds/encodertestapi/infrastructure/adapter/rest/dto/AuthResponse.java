package com.cds.encodertestapi.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for the authentication response
 */
@Schema(description = "Respuesta de autenticación")
public record AuthResponse(
        @Schema(description = "Token JWT para autenticación", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String token) {

    public String getToken() {
        return token;
    }
}