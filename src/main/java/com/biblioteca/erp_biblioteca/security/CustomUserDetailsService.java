package com.biblioteca.erp_biblioteca.security;

import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        return new User(usuario.getEmail(), 
                       usuario.getSenha(),
                       Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())));
    }

    public UserDetails loadUserById(String id) {
        Usuario usuario = usuarioRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o id: " + id));

        return new User(usuario.getEmail(), 
                       usuario.getSenha(),
                       Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())));
    }
}