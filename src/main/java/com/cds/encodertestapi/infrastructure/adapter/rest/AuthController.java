package com.cds.encodertestapi.infrastructure.adapter.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cds.encodertestapi.domain.model.Usuario;
import com.cds.encodertestapi.domain.port.AuthenticationService;
import com.cds.encodertestapi.infrastructure.adapter.rest.dto.AuthResponse;
import com.cds.encodertestapi.infrastructure.adapter.rest.dto.LoginRequest;
import com.cds.encodertestapi.infrastructure.adapter.rest.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication management")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Register user", description = "Registers a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Username or email already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        authenticationService.register(usuario);
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(new AuthResponse(token));
    }

}