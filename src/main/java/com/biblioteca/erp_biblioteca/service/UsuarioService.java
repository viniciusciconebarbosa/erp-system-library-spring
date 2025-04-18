package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        // Verifica se email já existe
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
            .idade(usuarioDTO.getIdade())
            .role(Role.COMUM) // Usuário comum por padrão
            .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario criarUsuarioAdmin(UsuarioDTO usuarioDTO) {
        // Verifica se email já existe
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
            .idade(usuarioDTO.getIdade())
            .role(Role.ADMIN)
            .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarRoleUsuario(UUID id, Role novaRole) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setRole(novaRole);
        return usuarioRepository.save(usuario);
    }
}
