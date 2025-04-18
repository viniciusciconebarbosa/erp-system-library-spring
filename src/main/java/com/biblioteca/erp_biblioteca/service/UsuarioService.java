package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario atualizarRoleUsuario(UUID id, Role novaRole) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setRole(novaRole);
        return usuarioRepository.save(usuario);
    }
}
