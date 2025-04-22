package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.enums.Role;
import com.biblioteca.erp_biblioteca.exception.BusinessException;
import com.biblioteca.erp_biblioteca.exception.EmailJaCadastradoException;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new EmailJaCadastradoException();
        }

        Usuario usuario = Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
            .idade(usuarioDTO.getIdade())
            .role(Role.COMUM)
            .build();

        return usuarioRepository.save(usuario);
    }

    public Usuario criarUsuarioAdmin(UsuarioDTO usuarioDTO) {
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

    public Usuario buscarUsuario(UUID id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizarUsuario(UUID id, UsuarioDTO usuarioDTO) {
        Usuario usuario = buscarUsuario(id);

        usuarioRepository.findByEmail(usuarioDTO.getEmail())
            .ifPresent(u -> {
                if (!u.getId().equals(id)) {
                    throw new RuntimeException("Email já está em uso");
                }
            });

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setIdade(usuarioDTO.getIdade());
        
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarRoleUsuario(UUID id, Role novaRole) {
        Usuario usuario = buscarUsuario(id);
        usuario.setRole(novaRole);
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
