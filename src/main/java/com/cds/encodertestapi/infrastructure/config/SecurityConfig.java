package com.cds.encodertestapi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.cds.encodertestapi.infrastructure.adapter.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // Enable CSRF with API-specific configuration
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/auth/**")
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                                // Configure protection against session fixation attacks
                                .headers(headers -> headers
                                                .contentSecurityPolicy(
                                                                csp -> csp.policyDirectives("default-src 'self'"))
                                                .frameOptions(frame -> frame.deny())
                                                .xssProtection(xss -> xss.disable())
                                                .contentTypeOptions(contentType -> contentType.disable()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/swagger-ui/**").permitAll()
                                                .requestMatchers("/swagger-ui.html").permitAll()
                                                .requestMatchers("/v3/api-docs/**").permitAll()
                                                .requestMatchers("/v3/api-docs.yaml").permitAll()
                                                .requestMatchers("/webjars/**").permitAll()
                                                .requestMatchers("/ws/**").permitAll()
                                                .requestMatchers("/static/**").permitAll()
                                                .requestMatchers("/*.html").permitAll()
                                                .requestMatchers("/coverage-report.html").permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}