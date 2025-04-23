package com.cds.encodertestapi.infrastructure.adapter.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cds.encodertestapi.domain.port.UsuarioRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .map(usuario -> new User(
                        usuario.getUsername(),
                        usuario.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}