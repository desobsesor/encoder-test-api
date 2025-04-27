package com.cds.encodertestapi.infrastructure.adapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for login request
 */
@Schema(description = "Login Request")
public record LoginRequest(
        @NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "The username can only contain letters, numbers and underscores") @Schema(description = "Nombre de usuario", example = "usuario1") String username,

        @NotBlank(message = "Password is required") @Size(min = 4, message = "The password must be at least 6 characters long") @Schema(description = "Password", example = "password123") String password) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}