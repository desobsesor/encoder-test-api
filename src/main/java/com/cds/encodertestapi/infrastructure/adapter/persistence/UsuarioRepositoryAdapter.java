package com.cds.encodertestapi.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.cds.encodertestapi.domain.model.Usuario;
import com.cds.encodertestapi.domain.port.UsuarioRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return jpaUsuarioRepository.findByUsername(username)
                .map(UsuarioEntity::toDomainModel);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = UsuarioEntity.fromDomainModel(usuario);
        UsuarioEntity savedEntity = jpaUsuarioRepository.save(usuarioEntity);
        return savedEntity.toDomainModel();
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUsuarioRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUsuarioRepository.existsByEmail(email);
    }
}