package com.cds.encodertestapi.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration request
 */
@Schema(description = "User registration request")
public record RegisterRequest(
        @NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres") @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "El nombre de usuario solo puede contener letras, números y guiones bajos") @Schema(description = "Username", example = "nuevoUsuario") String username,
        @NotBlank(message = "Password is required") @Size(min = 4, message = "La contraseña debe tener al menos 6 caracteres") @Schema(description = "Password", example = "password123") String password) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}