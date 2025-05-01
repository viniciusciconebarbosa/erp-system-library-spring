package com.biblioteca.erp_biblioteca.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioDTO {
    public interface Create {}
    public interface Update {}

    @NotBlank(message = "Nome é obrigatório", groups = {Create.class, Update.class})
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório", groups = {Create.class, Update.class})
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória", groups = Create.class)
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", 
        message = "Senha deve ter no mínimo 8 caracteres, contendo letras e números",
        groups = Create.class
    )
    private String senha;
    
    @NotNull(message = "Idade é obrigatória", groups = {Create.class, Update.class})
    @Min(value = 16, message = "Idade mínima é 16 anos")
    @Max(value = 120, message = "Idade máxima é 120 anos")
    private Integer idade;
}
