package com.biblioteca.erp_biblioteca.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private int idade;
}
