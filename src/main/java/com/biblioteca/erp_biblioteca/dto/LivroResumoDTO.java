package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.model.Livro;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroResumoDTO {
    private UUID id;
    private String titulo;
    private String autor;
    private String capaFoto;
    private boolean disponivelLocacao;
    
    public static LivroResumoDTO fromEntity(Livro livro, StorageConfig storageConfig) {
        return LivroResumoDTO.builder()
            .id(livro.getId())
            .titulo(livro.getTitulo())
            .autor(livro.getAutor())
            .capaFoto(storageConfig.getFullImageUrl(livro.getCapaFoto()))
            .disponivelLocacao(livro.isDisponivelLocacao())
            .build();
    }
}