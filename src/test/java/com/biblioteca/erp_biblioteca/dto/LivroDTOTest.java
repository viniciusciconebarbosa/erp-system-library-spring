package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

class LivroDTOTest {

    @Test
    @DisplayName("Deve criar LivroDTO com todos os campos")
    void deveCriarLivroDTOComTodosOsCampos() {
        // Arrange & Act
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("O Senhor dos Anéis");
        livroDTO.setAutor("J.R.R. Tolkien");
        livroDTO.setGenero(Genero.FICCAO);
        livroDTO.setCapaFoto("capa.jpg");
        livroDTO.setSinopse("Uma aventura épica na Terra Média");
        livroDTO.setClassificacaoEtaria(ClassificacaoEtaria.DEZOITO_ANOS);
        livroDTO.setEstadoConservacao(EstadoConservacao.NOVO);
        livroDTO.setDoadorId(java.util.UUID.randomUUID());

        // Assert
        assertEquals("O Senhor dos Anéis", livroDTO.getTitulo());
        assertEquals("J.R.R. Tolkien", livroDTO.getAutor());
        assertEquals(Genero.FICCAO, livroDTO.getGenero());
        assertEquals("capa.jpg", livroDTO.getCapaFoto());
        assertEquals("Uma aventura épica na Terra Média", livroDTO.getSinopse());
        assertEquals(ClassificacaoEtaria.DEZOITO_ANOS, livroDTO.getClassificacaoEtaria());
        assertEquals(EstadoConservacao.NOVO, livroDTO.getEstadoConservacao());
        assertNotNull(livroDTO.getDoadorId());
    }

    @Test
    @DisplayName("Deve criar LivroDTO vazio")
    void deveCriarLivroDTOVazio() {
        // Arrange & Act
        LivroDTO livroDTO = new LivroDTO();

        // Assert
        assertNull(livroDTO.getTitulo());
        assertNull(livroDTO.getAutor());
        assertNull(livroDTO.getGenero());
        assertNull(livroDTO.getCapaFoto());
        assertNull(livroDTO.getSinopse());
        assertNull(livroDTO.getClassificacaoEtaria());
        assertNull(livroDTO.getEstadoConservacao());
        assertNull(livroDTO.getDoadorId());
    }

    @Test
    @DisplayName("Deve permitir alterar valores do LivroDTO")
    void devePermitirAlterarValores() {
        // Arrange
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Original");
        livroDTO.setSinopse("Sinopse original");

        // Act
        livroDTO.setTitulo("Livro Atualizado");
        livroDTO.setSinopse("Nova sinopse");

        // Assert
        assertEquals("Livro Atualizado", livroDTO.getTitulo());
        assertEquals("Nova sinopse", livroDTO.getSinopse());
    }
}
