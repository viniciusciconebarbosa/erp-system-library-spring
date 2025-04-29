package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.enums.StatusLocacao;
import com.biblioteca.erp_biblioteca.model.Locacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocacaoResponse {
    private UUID id;
    private LivroResumoDTO livro;
    private UsuarioResumoDTO usuario;
    private Date dataLocacao;
    private Date dataDevolucao;
    private StatusLocacao status;

    public static LocacaoResponse fromEntity(Locacao locacao, StorageConfig storageConfig) {
        return LocacaoResponse.builder()
                .id(locacao.getId())
                .livro(LivroResumoDTO.builder()
                        .id(locacao.getLivro().getId())
                        .titulo(locacao.getLivro().getTitulo())
                        .autor(locacao.getLivro().getAutor())
                        .capaFoto(storageConfig.getFullImageUrl(locacao.getLivro().getCapaFoto()))
                        .disponivelLocacao(locacao.getLivro().isDisponivelLocacao())
                        .build())
                .usuario(UsuarioResumoDTO.builder()
                        .id(locacao.getUsuario().getId())
                        .nome(locacao.getUsuario().getNome())
                        .email(locacao.getUsuario().getEmail())
                        .build())
                .dataLocacao(locacao.getDataLocacao())
                .dataDevolucao(locacao.getDataDevolucao())
                .status(locacao.getStatus())
                .build();
    }
} 