package com.biblioteca.erp_biblioteca.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class EstadoConservacaoTest {

    @Test
    @DisplayName("Deve conter todos os estados de conservação esperados")
    void deveConterTodosOsEstadosDeConservacaoEsperados() {
        // Arrange & Act
        EstadoConservacao[] estados = EstadoConservacao.values();

        // Assert
        assertEquals(5, estados.length);
        assertTrue(containsEstado(estados, "NOVO"));
        assertTrue(containsEstado(estados, "OTIMO"));
        assertTrue(containsEstado(estados, "BOM"));
        assertTrue(containsEstado(estados, "REGULAR"));
        assertTrue(containsEstado(estados, "RUIM"));
    }

    @Test
    @DisplayName("Deve retornar estado por nome")
    void deveRetornarEstadoPorNome() {
        // Arrange & Act
        EstadoConservacao novo = EstadoConservacao.valueOf("NOVO");
        EstadoConservacao bom = EstadoConservacao.valueOf("BOM");

        // Assert
        assertEquals(EstadoConservacao.NOVO, novo);
        assertEquals(EstadoConservacao.BOM, bom);
    }

    @Test
    @DisplayName("Deve lançar exceção para estado inválido")
    void deveLancarExcecaoParaEstadoInvalido() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            EstadoConservacao.valueOf("ESTADO_INEXISTENTE");
        });
    }

    private boolean containsEstado(EstadoConservacao[] estados, String nome) {
        for (EstadoConservacao estado : estados) {
            if (estado.name().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
