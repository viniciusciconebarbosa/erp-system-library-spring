package com.biblioteca.erp_biblioteca.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioDTOTest {

    @Test
    @DisplayName("Deve criar UsuarioDTO com todos os campos")
    void deveCriarUsuarioDTOComTodosOsCampos() {
        // Arrange & Act
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setSenha("senha123");
        usuarioDTO.setIdade(30);

        // Assert
        assertEquals("João Silva", usuarioDTO.getNome());
        assertEquals("joao@email.com", usuarioDTO.getEmail());
        assertEquals("senha123", usuarioDTO.getSenha());
        assertEquals(30, usuarioDTO.getIdade());
    }

    @Test
    @DisplayName("Deve criar UsuarioDTO vazio")
    void deveCriarUsuarioDTOVazio() {
        // Arrange & Act
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        // Assert
        assertNull(usuarioDTO.getNome());
        assertNull(usuarioDTO.getEmail());
        assertNull(usuarioDTO.getSenha());
        assertNull(usuarioDTO.getIdade());
    }

    @Test
    @DisplayName("Deve permitir alterar valores do UsuarioDTO")
    void devePermitirAlterarValores() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Maria");
        usuarioDTO.setEmail("maria@email.com");

        // Act
        usuarioDTO.setNome("Maria Silva");
        usuarioDTO.setEmail("maria.silva@email.com");

        // Assert
        assertEquals("Maria Silva", usuarioDTO.getNome());
        assertEquals("maria.silva@email.com", usuarioDTO.getEmail());
    }
}
