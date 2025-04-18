package com.biblioteca.erp_biblioteca.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LocacaoDTO {
    private UUID livroId;
    private UUID usuarioId;
}