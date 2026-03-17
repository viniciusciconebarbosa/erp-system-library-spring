package com.biblioteca.erp_biblioteca.security;

import com.biblioteca.erp_biblioteca.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.UUID;

@Getter
public class UserPrincipal extends User {
    private final UUID id;

    public UserPrincipal(Usuario usuario) {
        super(usuario.getEmail(),
                usuario.getSenha(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name())));
        this.id = usuario.getId();
    }
}