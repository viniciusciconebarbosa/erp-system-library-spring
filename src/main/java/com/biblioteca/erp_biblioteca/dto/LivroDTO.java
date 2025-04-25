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

    @NotBlank(message = "Autor é obrigatório")
    @Size(min = 2, max = 255, message = "Autor deve ter entre 2 e 255 caracteres")
    private String autor;

    @NotNull(message = "Valores aceitos: [FICCAO, NAO_FICCAO, TERROR, ROMANCE, EDUCACAO, TECNICO]")
    private Genero genero;

    private String capaFoto;

    @Size(max = 2000, message = "Sinopse deve ter no máximo 2000 caracteres")
    private String sinopse;

    @NotNull(message = "Valores aceitos: [LIVRE, DOZE_ANOS, QUATORZE_ANOS, DEZESSEIS_ANOS, DEZOITO_ANOS]")
    private ClassificacaoEtaria classificacaoEtaria;

    @NotNull(message = "Valores aceitos: [NOVO, OTIMO, BOM, REGULAR, RUIM]")
    private EstadoConservacao estadoConservacao;

    private UUID doadorId;
}
