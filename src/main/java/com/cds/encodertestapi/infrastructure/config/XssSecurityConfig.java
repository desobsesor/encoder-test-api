package com.cds.encodertestapi.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Configuration to prevent XSS attacks
 */
@Configuration
public class XssSecurityConfig {

    /**
     * Registers a filter to set security headers in each HTTP response
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XssFilter());
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    /**
     * Filter that adds security headers to prevent XSS
     */
    public static class XssFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                FilterChain filterChain)
                throws ServletException, IOException {

            // Set security headers to prevent XSS
            response.setHeader("X-XSS-Protection", "1; mode=block");
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("X-Frame-Options", "DENY");

            // Apply a more permissive CSP for HTML reports and static resources
            String requestPath = request.getRequestURI();
            if (requestPath.endsWith(".html") || requestPath.contains("/static/") ||
                    requestPath.contains("/coverage-report")) {
                response.setHeader("Content-Security-Policy",
                        "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:");
            } else {
                response.setHeader("Content-Security-Policy", "default-src 'self'");
            }

            response.setHeader("Referrer-Policy", "no-referrer-when-downgrade");

            filterChain.doFilter(request, response);
        }
    }
}