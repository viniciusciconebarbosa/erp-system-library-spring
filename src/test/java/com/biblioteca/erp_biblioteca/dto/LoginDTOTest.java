package com.biblioteca.erp_biblioteca.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {

    @Test
    @DisplayName("Deve criar LoginDTO com email e senha")
    void deveCriarLoginDTOComEmailESenha() {
        // Arrange & Act
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("usuario@email.com");
        loginDTO.setSenha("senha123");

        // Assert
        assertEquals("usuario@email.com", loginDTO.getEmail());
        assertEquals("senha123", loginDTO.getSenha());
    }

    @Test
    @DisplayName("Deve criar LoginDTO vazio")
    void deveCriarLoginDTOVazio() {
        // Arrange & Act
        LoginDTO loginDTO = new LoginDTO();

        // Assert
        assertNull(loginDTO.getEmail());
        assertNull(loginDTO.getSenha());
    }

    @Test
    @DisplayName("Deve permitir alterar email e senha")
    void devePermitirAlterarEmailESenha() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("email1@test.com");
        loginDTO.setSenha("senha1");

        // Act
        loginDTO.setEmail("email2@test.com");
        loginDTO.setSenha("senha2");

        // Assert
        assertEquals("email2@test.com", loginDTO.getEmail());
        assertEquals("senha2", loginDTO.getSenha());
    }
}
