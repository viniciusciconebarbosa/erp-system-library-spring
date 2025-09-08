package com.biblioteca.erp_biblioteca.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    @DisplayName("Deve criar ApiResponse com todos os campos")
    void deveCriarApiResponseComTodosOsCampos() {
        // Arrange & Act
        ApiResponse response = new ApiResponse("Operação realizada com sucesso", "123", "success", "dados");

        // Assert
        assertEquals("Operação realizada com sucesso", response.getMessage());
        assertEquals("123", response.getId());
        assertEquals("success", response.getType());
        assertEquals("dados", response.getData());
    }

    @Test
    @DisplayName("Deve criar ApiResponse com construtor de 3 parâmetros")
    void deveCriarApiResponseComConstrutorDe3Parametros() {
        // Arrange & Act
        ApiResponse response = new ApiResponse("Erro na operação", "456", "error");

        // Assert
        assertEquals("Erro na operação", response.getMessage());
        assertEquals("456", response.getId());
        assertEquals("error", response.getType());
        assertNull(response.getData());
    }

    @Test
    @DisplayName("Deve permitir alterar valores do ApiResponse")
    void devePermitirAlterarValoresDoApiResponse() {
        // Arrange
        ApiResponse response = new ApiResponse("Mensagem original", "789", "info", "dados originais");

        // Act
        response.setMessage("Nova mensagem");
        response.setId("999");
        response.setType("warning");
        response.setData("novos dados");

        // Assert
        assertEquals("Nova mensagem", response.getMessage());
        assertEquals("999", response.getId());
        assertEquals("warning", response.getType());
        assertEquals("novos dados", response.getData());
    }
}
