package com.cds.encodertestapi.infrastructure.adapter.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cds.encodertestapi.infrastructure.adapter.rest.dto.LoginRequest;
import com.cds.encodertestapi.infrastructure.adapter.rest.dto.RegisterRequest;
import com.cds.encodertestapi.infrastructure.adapter.security.InputValidator;

@ExtendWith(MockitoExtension.class)
public class AuthControllerAdviceTest {

    @Mock
    private InputValidator inputValidator;

    @InjectMocks
    private AuthControllerAdvice authControllerAdvice;

    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest("testUser", "testPassword");
        registerRequest = new RegisterRequest("testUser", "testPassword");
    }

    @Test
    public void testValidateLoginRequest_InvalidUsername_ThrowsSecurityException() {

        loginRequest = new LoginRequest("userInvalid", "...");
        when(inputValidator.isValidUsername(loginRequest.getUsername())).thenReturn(false);

        assertThrows(SecurityException.class, () -> {
            authControllerAdvice.validateLoginRequest(loginRequest);
        });
    }

    @Test
    public void testValidateLoginRequest_InvalidPassword_ThrowsSecurityException() {
        assertThrows(SecurityException.class, () -> {
            authControllerAdvice.validateLoginRequest(loginRequest);
        });
    }

    @Test
    public void testValidateRegisterRequest_InvalidPassword_ThrowsSecurityException() {
        assertThrows(SecurityException.class, () -> {
            authControllerAdvice.validateRegisterRequest(registerRequest);
        });
    }
}