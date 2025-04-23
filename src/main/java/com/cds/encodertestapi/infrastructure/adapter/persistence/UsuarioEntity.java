package com.cds.encodertestapi.infrastructure.adapter.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    // Method to convert the entity to a domain model
    public com.cds.encodertestapi.domain.model.Usuario toDomainModel() {
        return com.cds.encodertestapi.domain.model.Usuario.builder()
                .userId(this.userId)
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .build();
    }

    // Method for creating an entity from a domain model
    public static UsuarioEntity fromDomainModel(com.cds.encodertestapi.domain.model.Usuario usuario) {
        return UsuarioEntity.builder()
                .userId(usuario.getUserId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .email(usuario.getEmail())
                .build();
    }
}