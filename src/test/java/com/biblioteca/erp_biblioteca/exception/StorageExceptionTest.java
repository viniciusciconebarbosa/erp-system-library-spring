package com.biblioteca.erp_biblioteca.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class StorageExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Erro de armazenamento";

        // Act
        StorageException exception = new StorageException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem customizada")
    void deveCriarExcecaoComMensagemCustomizada() {
        // Arrange
        String mensagem = "Erro de armazenamento";

        // Act
        StorageException exception = new StorageException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com causa")
    void deveCriarExcecaoComCausa() {
        // Arrange
        String mensagem = "Falha ao salvar arquivo";
        Throwable causa = new RuntimeException("Erro de I/O");

        // Act
        StorageException exception = new StorageException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
