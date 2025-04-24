package com.cds.encodertestapi.infrastructure.adapter.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cds.encodertestapi.infrastructure.adapter.rest.dto.LoginRequest;
import com.cds.encodertestapi.infrastructure.adapter.rest.dto.RegisterRequest;
import com.cds.encodertestapi.infrastructure.adapter.security.InputValidator;

import lombok.RequiredArgsConstructor;

/**
 * Class to validate inputs in the authentication controller
 * and handle security-related exceptions
 */
@ControllerAdvice(assignableTypes = AuthController.class)
@RequiredArgsConstructor
public class AuthControllerAdvice {

    private final InputValidator inputValidator;

    /**
     * Validates login requests before processing them
     * 
     * @param request Login request
     * @throws SecurityException If the request contains potentially dangerous data
     */
    @ModelAttribute
    public void validateLoginRequest(LoginRequest request) {
        if (request != null) {

            if (!inputValidator.isValidUsername(request.getUsername())) {
                throw new SecurityException("Nombre de usuario inválido");
            }

            // The password is not validated with patterns to allow complex passwords
            // but it is checked for potentially dangerous characters

            if (!inputValidator.isSafeString(request.getPassword())) {
                throw new SecurityException("Contraseña contiene caracteres no permitidos");
            }

        }
    }

    /**
     * Validates registration requests before processing them
     * 
     * @param request Registration request
     * @throws SecurityException If the request contains potentially dangerous data
     */
    @ModelAttribute
    public void validateRegisterRequest(RegisterRequest request) {
        if (request != null) {

            if (!inputValidator.isValidUsername(request.getUsername())) {
                throw new SecurityException("Nombre de usuario inválido");
            }

            if (!inputValidator.isSafeString(request.getPassword())) {
                throw new SecurityException("Contraseña contiene caracteres no permitidos");
            }

        }
    }

    /**
     * Handles security exceptions
     * 
     * @param ex Security exception
     * @return Response with error message
     */
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleSecurityException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}