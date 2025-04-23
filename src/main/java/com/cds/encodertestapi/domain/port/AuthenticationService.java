package com.cds.encodertestapi.domain.port;

import com.cds.encodertestapi.domain.model.Usuario;

public interface AuthenticationService {
    /**
     * Authenticates a user with their credentials
     * 
     * @param username Username
     * @param password Password
     * @return JWT Token if authentication is successful
     * @throws RuntimeException if credentials are invalid
     */
    String authenticate(String username, String password);

    /**
     * Registers a new user in the system
     * 
     * @param usuario User data to register
     * @return Registered user
     * @throws RuntimeException if username or email already exist
     */
    Usuario register(Usuario usuario);

    /**
     * Validates a JWT token
     * 
     * @param token JWT Token to validate
     * @return true if the token is valid, false otherwise
     */
    boolean validateToken(String token);

    /**
     * Gets the username from a JWT token
     * 
     * @param token JWT Token
     * @return Username
     */
    String getUsernameFromToken(String token);
}