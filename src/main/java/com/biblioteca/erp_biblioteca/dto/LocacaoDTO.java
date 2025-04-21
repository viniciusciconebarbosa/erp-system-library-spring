package com.biblioteca.erp_biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class LocacaoDTO {
    @NotNull(message = "ID do livro é obrigatório")
    private UUID livroId;

    @NotNull(message = "ID do usuário é obrigatório")
    private UUID usuarioId;
}