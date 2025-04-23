package com.cds.encodertestapi.domain.port;

import java.util.Optional;

import com.cds.encodertestapi.domain.model.Usuario;

public interface UsuarioRepository {
    Optional<Usuario> findByUsername(String username);

    Usuario save(Usuario usuario);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}