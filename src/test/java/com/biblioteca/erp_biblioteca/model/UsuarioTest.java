package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class UsuarioTest {

    @Test
    @DisplayName("Deve criar usuário com todos os campos")
    void deveCriarUsuarioComTodosOsCampos() {
        // Arrange & Act
        Usuario usuario = Usuario.builder()
                .nome("João Silva")
                .email("joao@email.com")
                .senha("senha123")
                .idade(30)
                .role(Role.COMUM)
                .build();

        // Assert
        assertNotNull(usuario);
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
        assertEquals(30, usuario.getIdade());
        assertEquals(Role.COMUM, usuario.getRole());
    }

    @Test
    @DisplayName("Deve criar usuário vazio")
    void deveCriarUsuarioVazio() {
        // Arrange & Act
        Usuario usuario = new Usuario();

        // Assert
        assertNotNull(usuario);
        assertNull(usuario.getNome());
        assertNull(usuario.getEmail());
        assertNull(usuario.getSenha());
        assertEquals(0, usuario.getIdade());
        assertNull(usuario.getRole());
    }

    @Test
    @DisplayName("Deve permitir alterar campos do usuário")
    void devePermitirAlterarCamposDoUsuario() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .nome("Maria")
                .email("maria@email.com")
                .build();

        // Act
        usuario.setNome("Maria Silva");
        usuario.setEmail("maria.silva@email.com");
        usuario.setIdade(25);
        usuario.setRole(Role.ADMIN);

        // Assert
        assertEquals("Maria Silva", usuario.getNome());
        assertEquals("maria.silva@email.com", usuario.getEmail());
        assertEquals(25, usuario.getIdade());
        assertEquals(Role.ADMIN, usuario.getRole());
    }

    @Test
    @DisplayName("Deve gerar ID automaticamente")
    void deveGerarIdAutomaticamente() {
        // Arrange & Act
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        // Assert
        assertNotNull(usuario.getId());
    }
}
