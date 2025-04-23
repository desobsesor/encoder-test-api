package com.cds.encodertestapi.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user registration request
 */
@Schema(description = "User registration request")
public record RegisterRequest(
        @Schema(description = "Username", example = "nuevoUsuario") String username,

        @Schema(description = "Password", example = "password123") String password,

        @Schema(description = "Email", example = "usuario@ejemplo.com") String email) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}