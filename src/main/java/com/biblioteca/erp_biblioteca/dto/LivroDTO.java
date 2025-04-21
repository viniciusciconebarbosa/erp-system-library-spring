package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotNull(message = "Valores aceitos: [FICCAO, NAO_FICCAO, TERROR, ROMANCE, EDUCACAO, TECNICO]")
    private Genero genero;

    @NotBlank(message = "URL da capa é obrigatória")
    @Size(max = 255, message = "URL da capa deve ter no máximo 255 caracteres")
    private String capaFoto;

    @NotNull(message = "Valores aceitos: [LIVRE, DOZE_ANOS, QUATORZE_ANOS, DEZESSEIS_ANOS, DEZOITO_ANOS]")
    private ClassificacaoEtaria classificacaoEtaria;

    @NotNull(message = "Valores aceitos: [NOVO, OTIMO, BOM, REGULAR, RUIM]")
    private EstadoConservacao estadoConservacao;

    @Schema(description = "ID do usuário doador (opcional)")
    private UUID doadorId;
}
