package com.biblioteca.erp_biblioteca.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ClassificacaoEtariaTest {

    @Test
    @DisplayName("Deve conter todas as classificações etárias esperadas")
    void deveConterTodasAsClassificacoesEtariasEsperadas() {
        // Arrange & Act
        ClassificacaoEtaria[] classificacoes = ClassificacaoEtaria.values();

        // Assert
        assertEquals(6, classificacoes.length);
        assertTrue(containsClassificacao(classificacoes, "LIVRE"));
        assertTrue(containsClassificacao(classificacoes, "DEZ_ANOS"));
        assertTrue(containsClassificacao(classificacoes, "DOZE_ANOS"));
        assertTrue(containsClassificacao(classificacoes, "QUATORZE_ANOS"));
        assertTrue(containsClassificacao(classificacoes, "DEZESSEIS_ANOS"));
        assertTrue(containsClassificacao(classificacoes, "DEZOITO_ANOS"));
    }

    @Test
    @DisplayName("Deve retornar classificação por nome")
    void deveRetornarClassificacaoPorNome() {
        // Arrange & Act
        ClassificacaoEtaria livre = ClassificacaoEtaria.valueOf("LIVRE");
        ClassificacaoEtaria dezoitoAnos = ClassificacaoEtaria.valueOf("DEZOITO_ANOS");

        // Assert
        assertEquals(ClassificacaoEtaria.LIVRE, livre);
        assertEquals(ClassificacaoEtaria.DEZOITO_ANOS, dezoitoAnos);
    }

    @Test
    @DisplayName("Deve lançar exceção para classificação inválida")
    void deveLancarExcecaoParaClassificacaoInvalida() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ClassificacaoEtaria.valueOf("CLASSIFICACAO_INEXISTENTE");
        });
    }

    private boolean containsClassificacao(ClassificacaoEtaria[] classificacoes, String nome) {
        for (ClassificacaoEtaria classificacao : classificacoes) {
            if (classificacao.name().equals(nome)) {
                return true;
            }
        }
        return false;
    }
}
