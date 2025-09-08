package com.biblioteca.erp_biblioteca.model;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class LivroTest {

    @Test
    @DisplayName("Deve criar livro com todos os campos")
    void deveCriarLivroComTodosOsCampos() {
        // Arrange & Act
        Livro livro = Livro.builder()
                .titulo("O Senhor dos Anéis")
                .autor("J.R.R. Tolkien")
                .genero(Genero.FICCAO)
                .capaFoto("capa.jpg")
                .disponivelLocacao(true)
                .sinopse("Uma aventura épica na Terra Média")
                .classificacaoEtaria(ClassificacaoEtaria.DEZOITO_ANOS)
                .estadoConservacao(EstadoConservacao.NOVO)
                .build();

        // Assert
        assertNotNull(livro);
        assertEquals("O Senhor dos Anéis", livro.getTitulo());
        assertEquals("J.R.R. Tolkien", livro.getAutor());
        assertEquals(Genero.FICCAO, livro.getGenero());
        assertEquals("capa.jpg", livro.getCapaFoto());
        assertTrue(livro.isDisponivelLocacao());
        assertEquals("Uma aventura épica na Terra Média", livro.getSinopse());
        assertEquals(ClassificacaoEtaria.DEZOITO_ANOS, livro.getClassificacaoEtaria());
        assertEquals(EstadoConservacao.NOVO, livro.getEstadoConservacao());
    }

    @Test
    @DisplayName("Deve criar livro vazio")
    void deveCriarLivroVazio() {
        // Arrange & Act
        Livro livro = new Livro();

        // Assert
        assertNotNull(livro);
        assertNull(livro.getTitulo());
        assertNull(livro.getAutor());
        assertNull(livro.getGenero());
        assertNull(livro.getCapaFoto());
        assertFalse(livro.isDisponivelLocacao());
        assertNull(livro.getSinopse());
        assertNull(livro.getClassificacaoEtaria());
        assertNull(livro.getEstadoConservacao());
    }

    @Test
    @DisplayName("Deve permitir alterar campos do livro")
    void devePermitirAlterarCamposDoLivro() {
        // Arrange
        Livro livro = Livro.builder()
                .titulo("Livro Original")
                .disponivelLocacao(false)
                .build();

        // Act
        livro.setTitulo("Livro Atualizado");
        livro.setDisponivelLocacao(true);
        livro.setGenero(Genero.FICCAO);
        livro.setClassificacaoEtaria(ClassificacaoEtaria.DOZE_ANOS);

        // Assert
        assertEquals("Livro Atualizado", livro.getTitulo());
        assertTrue(livro.isDisponivelLocacao());
        assertEquals(Genero.FICCAO, livro.getGenero());
        assertEquals(ClassificacaoEtaria.DOZE_ANOS, livro.getClassificacaoEtaria());
    }

    @Test
    @DisplayName("Deve gerar ID automaticamente")
    void deveGerarIdAutomaticamente() {
        // Arrange & Act
        Livro livro = new Livro();
        livro.setId(UUID.randomUUID());

        // Assert
        assertNotNull(livro.getId());
    }
}
