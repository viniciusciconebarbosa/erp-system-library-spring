package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import lombok.Data;

import java.util.UUID;

@Data
public class LivroDTO {
    private String titulo;
    private Genero genero;
    private String capaFoto;
    private ClassificacaoEtaria classificacaoEtaria;
    private EstadoConservacao estadoConservacao;
    private UUID doadorId;
}