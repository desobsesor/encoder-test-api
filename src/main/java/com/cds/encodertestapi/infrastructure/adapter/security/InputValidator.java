package com.cds.encodertestapi.infrastructure.adapter.security;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * Component to validate and sanitize user inputs
 * to prevent injection attacks
 */
@Component
public class InputValidator {

    // Patterns for input validation
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*$");
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern SAFE_STRING = Pattern.compile("^[^<>\\\"'%;()&+]*$");

    /**
     * Validates that a username meets the secure pattern
     * 
     * @param username Username to validate
     * @return true if the username is valid
     */
    public boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Validates that an email meets the secure pattern
     * 
     * @param email Email to validate
     * @return true if the email is valid
     */
    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates that a string does not contain potentially dangerous characters
     * 
     * @param input String to validate
     * @return true if the string is safe
     */
    public boolean isSafeString(String input) {
        return input != null && SAFE_STRING.matcher(input).matches();
    }

    /**
     * Sanitizes a string to prevent XSS
     * 
     * @param input String to sanitize
     * @return Sanitized string
     */
    public String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#x27;")
                .replaceAll("&", "&amp;")
                .replaceAll("/", "&#x2F;");
    }
}