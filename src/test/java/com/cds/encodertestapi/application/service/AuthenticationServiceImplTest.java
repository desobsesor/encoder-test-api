package com.cds.encodertestapi.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.cds.encodertestapi.domain.model.Usuario;
import com.cds.encodertestapi.domain.port.UsuarioRepository;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

class AuthenticationServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Generate a secure key for HS512 algorithm and encode it to Base64
        String secureKey = Base64.getEncoder().encodeToString(
                Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());

        // Set the secure key for testing
        ReflectionTestUtils.setField(authenticationService, "jwtSecret", secureKey);
        ReflectionTestUtils.setField(authenticationService, "jwtExpiration", 86400000L);
    }

    @Test
    void authenticate_WithValidCredentials_ReturnsToken() {
        // Arrange
        String username = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";

        Usuario usuario = Usuario.builder()
                .userId(1L)
                .username(username)
                .password(encodedPassword)
                .email("test@example.com")
                .build();

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // Act
        String token = authenticationService.authenticate(username, password);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        verify(usuarioRepository).findByUsername(username);
        verify(passwordEncoder).matches(password, encodedPassword);
    }

    @Test
    void authenticate_WithInvalidCredentials_ThrowsException() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";
        String encodedPassword = "encodedPassword";

        Usuario usuario = Usuario.builder()
                .userId(1L)
                .username(username)
                .password(encodedPassword)
                .email("test@example.com")
                .build();

        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authenticationService.authenticate(username, password);
        });

        verify(usuarioRepository).findByUsername(username);
        verify(passwordEncoder).matches(password, encodedPassword);
    }

    @Test
    void register_WithValidData_ReturnsRegisteredUser() {
        // Arrange
        String username = "newuser";
        String password = "password";
        String email = "new@example.com";
        String encodedPassword = "encodedPassword";

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        Usuario savedUsuario = Usuario.builder()
                .userId(1L)
                .username(username)
                .password(encodedPassword)
                .email(email)
                .build();

        when(usuarioRepository.existsByUsername(username)).thenReturn(false);
        when(usuarioRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUsuario);

        // Act
        Usuario result = authenticationService.register(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(username, result.getUsername());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(email, result.getEmail());

        verify(usuarioRepository).existsByUsername(username);
        verify(usuarioRepository).existsByEmail(email);
        verify(passwordEncoder).encode(password);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void register_WithExistingUsername_ThrowsException() {
        // Arrange
        String username = "existinguser";
        String password = "password";
        String email = "new@example.com";

        Usuario usuario = Usuario.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        when(usuarioRepository.existsByUsername(username)).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.register(usuario);
        });

        assertEquals("The username already exists", exception.getMessage());
        verify(usuarioRepository).existsByUsername(username);
        verify(usuarioRepository, never()).existsByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}