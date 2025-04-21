package com.biblioteca.erp_biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO para criação e atualização de locações")
public class LocacaoDTO {
    @Schema(description = "ID do livro a ser locado", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "ID do livro é obrigatório")
    private UUID livroId;

    @Schema(description = "ID do usuário que está realizando a locação", example = "123e4567-e89b-12d3-a456-426614174000")
    @NotNull(message = "ID do usuário é obrigatório")
    private UUID usuarioId;
}
