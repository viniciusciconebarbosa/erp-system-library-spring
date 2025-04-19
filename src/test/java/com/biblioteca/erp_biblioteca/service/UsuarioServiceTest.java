package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.UsuarioDTO;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Test User");
        usuarioDTO.setEmail("test@example.com");
        usuarioDTO.setSenha("password123");
        usuarioDTO.setIdade(25);
    }

    @Test
    void deveCriarUsuarioComSucesso() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenAnswer(i -> {
            Usuario u = (Usuario) i.getArguments()[0];
            u.setId(UUID.randomUUID());
            return u;
        });

        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);

        assertNotNull(usuario.getId());
        assertEquals(usuarioDTO.getNome(), usuario.getNome());
        assertEquals(usuarioDTO.getEmail(), usuario.getEmail());
    }

    @Test
    void deveLancarExcecaoAoCriarUsuarioComEmailDuplicado() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail()))
                .thenReturn(Optional.of(new Usuario()));

        assertThrows(RuntimeException.class, () -> {
            usuarioService.criarUsuario(usuarioDTO);
        });
    }
}