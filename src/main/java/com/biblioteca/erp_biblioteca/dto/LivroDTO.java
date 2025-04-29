package com.biblioteca.erp_biblioteca.dto;

import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    public interface Create extends Default {}
    public interface Update extends Default {}

    @NotBlank(message = "Título é obrigatório", groups = Create.class)
    @Size(min = 2, max = 255, message = "Título deve ter entre 2 e 255 caracteres")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório", groups = Create.class)
    @Size(min = 2, max = 255, message = "Autor deve ter entre 2 e 255 caracteres")
    private String autor;

    @NotNull(message = "Gênero é obrigatório", groups = Create.class)
    private Genero genero;

    private String capaFoto;

    @Size(max = 2000, message = "Sinopse deve ter no máximo 2000 caracteres")
    private String sinopse;

    @NotNull(message = "Classificação etária é obrigatória", groups = Create.class)
    private ClassificacaoEtaria classificacaoEtaria;

    @NotNull(message = "Estado de conservação é obrigatório", groups = Create.class)
    private EstadoConservacao estadoConservacao;

    private UUID doadorId;
}
