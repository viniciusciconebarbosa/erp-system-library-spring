package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class LivroDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(min = 2, max = 255, message = "Título deve ter entre 2 e 255 caracteres")
    private String titulo;

    @NotNull(message = "Gênero é obrigatório")
    private Genero genero;

    @NotBlank(message = "URL da capa é obrigatória")
    @Size(max = 255, message = "URL da capa deve ter no máximo 255 caracteres")
    private String capaFoto;

    @NotNull(message = "Classificação etária é obrigatória")
    private ClassificacaoEtaria classificacaoEtaria;

    @NotNull(message = "Estado de conservação é obrigatório")
    private EstadoConservacao estadoConservacao;

    @NotNull(message = "ID do doador é obrigatório")
    private UUID doadorId;
}
