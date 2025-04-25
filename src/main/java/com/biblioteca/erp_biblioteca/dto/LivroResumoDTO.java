package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.model.Livro;
import lombok.Data;

import java.util.UUID;


@Data
public class LivroResumoDTO {
    private UUID id;
    private String titulo;
    private String autor;
    private String capaFoto;
    private boolean disponivelLocacao;

    public static LivroResumoDTO fromLivro(Livro livro) {
        LivroResumoDTO dto = new LivroResumoDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setCapaFoto(livro.getCapaFoto());
        dto.setDisponivelLocacao(livro.isDisponivelLocacao());
        return dto;
    }
}
