package com.biblioteca.erp_biblioteca.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GeneroTest {

    @Test
    @DisplayName("Deve conter todos os gêneros esperados")
    void deveConterTodosOsGenerosEsperados() {
        // Arrange & Act
        Genero[] generos = Genero.values();

        // Assert
        assertEquals(6, generos.length);
        assertTrue(containsGenero(generos, "FICCAO"));
        assertTrue(containsGenero(generos, "NAO_FICCAO"));
        assertTrue(containsGenero(generos, "TERROR"));
        assertTrue(containsGenero(generos, "ROMANCE"));
        assertTrue(containsGenero(generos, "EDUCACAO"));
        assertTrue(containsGenero(generos, "TECNICO"));
    }

    @Test
    @DisplayName("Deve retornar gênero por nome")
    void deveRetornarGeneroPorNome() {
        // Arrange & Act
        Genero ficcao = Genero.valueOf("FICCAO");
        Genero terror = Genero.valueOf("TERROR");

        // Assert
        assertEquals(Genero.FICCAO, ficcao);
        assertEquals(Genero.TERROR, terror);
    }

    @Test
    @DisplayName("Deve lançar exceção para gênero inválido")
    void deveLancarExcecaoParaGeneroInvalido() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            Genero.valueOf("GENERO_INEXISTENTE");
        });
    }

    private boolean containsGenero(Genero[] generos, String nome) {
        for (Genero genero : generos) {
            if (genero.name().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
