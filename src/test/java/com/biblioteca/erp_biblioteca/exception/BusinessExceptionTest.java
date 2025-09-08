package com.biblioteca.erp_biblioteca.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Erro de negócio";

        // Act
        BusinessException exception = new BusinessException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem customizada")
    void deveCriarExcecaoComMensagemCustomizada() {
        // Arrange
        String mensagem = "Erro de negócio";

        // Act
        BusinessException exception = new BusinessException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem diferente")
    void deveCriarExcecaoComMensagemDiferente() {
        // Arrange
        String mensagem = "Erro específico de negócio";

        // Act
        BusinessException exception = new BusinessException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }
}
