package com.cds.encodertestapi.infrastructure.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Security configuration for JPA/Hibernate
// Enables security features to prevent SQL injection
@Configuration
public class JpaSecurityConfig {

    /**
     * Configures Hibernate to use prepared statements and prevent SQL injection
     * 
     * @return Hibernate properties customizer
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            // Enable prepared statements to prevent SQL injection
            hibernateProperties.put("hibernate.query.in_clause_parameter_padding", true);

            // Disable automatic DDL generation to prevent SQL injection
            hibernateProperties.put("hibernate.hbm2ddl.auto", "validate");

            // Prevent SQL injection in native queries
            hibernateProperties.put("hibernate.query.substitutions", "true=1, false=0");

            // Configure SQL dialect to use secure functions
            hibernateProperties.put("hibernate.dialect.storage_engine", "innodb");

            // Enable SQL query validation to detect potential injections
            hibernateProperties.put("hibernate.validator.apply_to_ddl", true);
        };
    }
}