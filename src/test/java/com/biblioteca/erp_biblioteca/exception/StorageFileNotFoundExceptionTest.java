package com.biblioteca.erp_biblioteca.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class StorageFileNotFoundExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Arquivo não encontrado";

        // Act
        StorageFileNotFoundException exception = new StorageFileNotFoundException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem customizada")
    void deveCriarExcecaoComMensagemCustomizada() {
        // Arrange
        String mensagem = "Arquivo não encontrado";

        // Act
        StorageFileNotFoundException exception = new StorageFileNotFoundException(mensagem);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar exceção com causa")
    void deveCriarExcecaoComCausa() {
        // Arrange
        String mensagem = "Arquivo não existe";
        Throwable causa = new RuntimeException("Erro de arquivo");

        // Act
        StorageFileNotFoundException exception = new StorageFileNotFoundException(mensagem, causa);

        // Assert
        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
