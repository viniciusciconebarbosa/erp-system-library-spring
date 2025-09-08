package com.biblioteca.erp_biblioteca.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EmailJaCadastradoExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem padrão")
    void deveCriarExcecaoComMensagemPadrao() {
        // Arrange & Act
        EmailJaCadastradoException exception = new EmailJaCadastradoException();

        // Assert
        assertNotNull(exception);
        assertEquals("Email já cadastrado no sistema", exception.getMessage());
    }

    @Test
    @DisplayName("Deve verificar se é RuntimeException")
    void deveVerificarSeERuntimeException() {
        // Arrange & Act
        EmailJaCadastradoException exception = new EmailJaCadastradoException();

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve ter mensagem não nula")
    void deveTerMensagemNaoNula() {
        // Arrange & Act
        EmailJaCadastradoException exception = new EmailJaCadastradoException();

        // Assert
        assertNotNull(exception.getMessage());
        assertFalse(exception.getMessage().isEmpty());
    }
}
