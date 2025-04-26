package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.model.Livro;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

@Getter
@Setter
@Builder
public class LivroResponse {
    private UUID id;
    private String titulo;
    private String autor;
    private String genero;
    private String capaFoto; // URL completa da imagem
    private boolean disponivelLocacao;
    private String classificacaoEtaria;
    private String estadoConservacao;
    private String sinopse;
    
    public static LivroResponse fromEntity(Livro livro, StorageConfig storageConfig) {
        return LivroResponse.builder()
            .id(livro.getId())
            .titulo(livro.getTitulo())
            .autor(livro.getAutor())
            .genero(livro.getGenero().name())
            .capaFoto(storageConfig.getFullImageUrl(livro.getCapaFoto()))
            .disponivelLocacao(livro.isDisponivelLocacao())
            .classificacaoEtaria(livro.getClassificacaoEtaria().name())
            .estadoConservacao(livro.getEstadoConservacao().name())
            .sinopse(livro.getSinopse())
            .build();
    }
}