package com.cds.encodertestapi.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for login request
 */
@Schema(description = "Solicitud de inicio de sesión")
public record LoginRequest(
        @Schema(description = "Nombre de usuario", example = "usuario1") String username,

        @Schema(description = "Contraseña", example = "password123") String password) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}