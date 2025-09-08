package com.biblioteca.erp_biblioteca.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class StatusLocacaoTest {

    @Test
    @DisplayName("Deve conter todos os status de locação esperados")
    void deveConterTodosOsStatusDeLocacaoEsperados() {
        // Arrange & Act
        StatusLocacao[] status = StatusLocacao.values();

        // Assert
        assertEquals(4, status.length);
        assertTrue(containsStatus(status, "ATIVA"));
        assertTrue(containsStatus(status, "FINALIZADA"));
        assertTrue(containsStatus(status, "ATRASADA"));
        assertTrue(containsStatus(status, "CANCELADA"));
    }

    @Test
    @DisplayName("Deve retornar status por nome")
    void deveRetornarStatusPorNome() {
        // Arrange & Act
        StatusLocacao ativa = StatusLocacao.valueOf("ATIVA");
        StatusLocacao finalizada = StatusLocacao.valueOf("FINALIZADA");

        // Assert
        assertEquals(StatusLocacao.ATIVA, ativa);
        assertEquals(StatusLocacao.FINALIZADA, finalizada);
    }

    @Test
    @DisplayName("Deve lançar exceção para status inválido")
    void deveLancarExcecaoParaStatusInvalido() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            StatusLocacao.valueOf("STATUS_INEXISTENTE");
        });
    }

    private boolean containsStatus(StatusLocacao[] status, String nome) {
        for (StatusLocacao s : status) {
            if (s.name().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
