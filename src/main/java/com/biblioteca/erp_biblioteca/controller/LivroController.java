package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.dto.LivroResumoDTO;
import com.biblioteca.erp_biblioteca.exception.BusinessException;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        @ApiResponse(responseCode = "200", description = "Livro cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou arquivo de capa com formato não permitido"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM")
    })
    public ResponseEntity<Livro> cadastrarLivro(
            @Valid @RequestPart("livro")
            @Parameter(description = "Dados do livro em formato JSON") LivroDTO livroDTO,

            @RequestPart(value = "capa", required = false)
            @Parameter(description = "Arquivo único de imagem para capa do livro (opcional). Formatos: JPG, PNG, GIF, WEBP. Tamanho máximo: 5MB")
            MultipartFile capaFile) {

    
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
        return ResponseEntity.ok(novoLivro);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Busca um livro específico",
        description = "Retorna os detalhes completos de um livro específico baseado em seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> buscarLivro(
        @Parameter(description = "ID do livro", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        @PathVariable UUID id
    ) {
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    @Operation(
        summary = "Lista todos os livros",
        description = "Retorna uma lista de livros. Use o parâmetro disponiveis=true para filtrar apenas livros disponíveis"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    })
    public ResponseEntity<List<LivroResumoDTO>> listarLivros(
        @Parameter(
            description = "Filtro de disponibilidade. Se true, retorna apenas livros disponíveis",
            example = "true"
        )
        @RequestParam(name = "disponiveis", required = false) Boolean disponiveis
    ) {
        List<Livro> livros = Boolean.TRUE.equals(disponiveis) ? 
            livroService.listarDisponiveis() : 
            livroService.listarTodos();
        
        List<LivroResumoDTO> resumos = livros.stream()
            .map(LivroResumoDTO::fromLivro)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(resumos);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Atualiza um livro",
        description = "Permite a atualização dos dados de um livro existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> atualizarLivro(
        @Parameter(description = "ID do livro", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        @PathVariable UUID id,
        @Parameter(description = "Dados atualizados do livro")
        @RequestBody LivroDTO livroDTO
    ) {
        return ResponseEntity.ok(livroService.atualizarLivro(id, livroDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Remove um livro",
        description = "Permite a remoção de um livro do sistema. Apenas administradores podem realizar esta operação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro removido com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<?> deletarLivro(
        @Parameter(description = "ID do livro", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        @PathVariable UUID id
    ) {
        livroService.deletarLivro(id);
        return ResponseEntity.ok().build();
    }
}
