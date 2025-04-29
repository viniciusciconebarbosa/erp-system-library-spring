package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.dto.ApiResponse;
import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.dto.LivroResponse;
import com.biblioteca.erp_biblioteca.dto.LivroResumoDTO;
import com.biblioteca.erp_biblioteca.exception.BusinessException;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "API para gerenciamento de livros da biblioteca")
public class LivroController {
    private final LivroService livroService;
    private final StorageConfig storageConfig;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Cadastra um novo livro",
        description = "Permite o cadastro de um novo livro com seus dados básicos e uma imagem de capa opcional"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Livro cadastrado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<ApiResponse> cadastrarLivro(
            @Validated(LivroDTO.Create.class) @RequestPart("livro") 
            @Parameter(
                description = "Dados do livro em formato JSON",
                required = true,
                content = @Content(schema = @Schema(implementation = LivroDTO.class))
            ) LivroDTO livroDTO,
            
            @RequestPart(value = "capa", required = false)
            @Parameter(
                description = "Arquivo único de imagem para capa do livro (opcional)",
                content = @Content(mediaType = "multipart/form-data")
            ) MultipartFile capaFile) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        try {
            if (request.getParts().stream()
                    .filter(part -> "capa".equals(part.getName()))
                    .count() > 1) {
                throw new BusinessException("Apenas um arquivo de capa é permitido");
            }
        } catch (IOException | ServletException e) {
            throw new BusinessException("Erro ao processar arquivo de upload: " + e.getMessage());
        }

        if (capaFile != null && !capaFile.isEmpty()) {
            String contentType = capaFile.getContentType();
            if (!storageConfig.getAllowedContentTypes().contains(contentType)) {
                throw new BusinessException("Formato de arquivo não permitido. Use apenas: " +
                        String.join(", ", storageConfig.getAllowedContentTypes()));
            }
        }

        Livro novoLivro = livroService.cadastrarLivro(livroDTO, capaFile);
        ApiResponse response = new ApiResponse(
            "Livro cadastrado com sucesso", 
            novoLivro.getId().toString(), 
            "SUCCESS"
        );
        response.setData(LivroResponse.fromEntity(novoLivro, storageConfig));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(
        summary = "Lista resumo dos livros",
        description = "Retorna uma lista resumida de livros para exibição na página inicial"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de livros retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = LivroResumoDTO.class))
            )
        )
    })
    public ResponseEntity<List<LivroResumoDTO>> listarLivros(
        @Parameter(
            description = "Filtro de disponibilidade. Se true, retorna apenas livros disponíveis",
            example = "true"
        )
        @RequestParam(name = "disponiveis", required = false) Boolean disponiveis) {
        
        List<LivroResumoDTO> livros = Boolean.TRUE.equals(disponiveis) ? 
            livroService.listarDisponiveisResumido() :
            livroService.listarTodosResumido();
        
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Busca um livro específico",
        description = "Retorna os detalhes completos de um livro específico baseado em seu ID"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Livro encontrado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LivroResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Livro não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    public ResponseEntity<LivroResponse> buscarLivro(
        @Parameter(
            description = "ID do livro",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID id) {
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(LivroResponse.fromEntity(livro, storageConfig));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Atualiza um livro",
        description = "Permite a atualização dos dados de um livro existente"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Livro atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acesso negado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<ApiResponse> atualizarLivro(
        @Parameter(
            description = "ID do livro",
            example = "123e4567-e89b-12d3-a456-426614174000",
            required = true
        )
        @PathVariable UUID id,
        @Validated(LivroDTO.Update.class) @RequestBody 
        @Parameter(
            description = "Dados atualizados do livro",
            required = true
        ) LivroDTO livroDTO) {
        
        Livro livroAtualizado = livroService.atualizarLivro(id, livroDTO);
        ApiResponse response = new ApiResponse(
            "Livro atualizado com sucesso", 
            livroAtualizado.getId().toString(), 
            "SUCCESS"
        );
        response.setData(LivroResponse.fromEntity(livroAtualizado, storageConfig));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Remove um livro",
        description = "Remove um livro do sistema"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Livro removido com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Acesso negado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<ApiResponse> removerLivro(@PathVariable UUID id) {
        livroService.deletarLivro(id);
        return ResponseEntity.ok(new ApiResponse(
            "Livro deletado com sucesso", 
            id.toString(), 
            "SUCCESS"
        ));
    }
}
