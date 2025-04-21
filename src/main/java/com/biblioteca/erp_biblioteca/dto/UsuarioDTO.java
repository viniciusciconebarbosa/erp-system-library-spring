package com.biblioteca.erp_biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", 
        message = "Senha deve ter no mínimo 8 caracteres, contendo letras e números"
    )
    private String senha;
    
    @NotNull(message = "Idade é obrigatória")
    @Min(value = 16, message = "Idade mínima é 16 anos")
    @Max(value = 120, message = "Idade máxima é 120 anos")
    private Integer idade;
}
