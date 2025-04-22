package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.dto.DeleteResponse;
import com.biblioteca.erp_biblioteca.dto.LivroDTO;
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

import java.io.IOException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;
    private final StorageConfig storageConfig;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(summary = "Cadastra um novo livro", description = "Cadastra um livro com dados básicos e uma imagem de capa opcional")
    public ResponseEntity<Livro> cadastrarLivro(
            @Valid @RequestPart("livro")
            @Parameter(description = "Dados do livro em formato JSON") LivroDTO livroDTO,

            @RequestPart(value = "capa", required = false)
            @Parameter(description = "Arquivo único de imagem para capa do livro (opcional). Formatos: JPG, PNG, GIF, WEBP. Tamanho máximo: 5MB")
            MultipartFile capaFile) {

        // Validação para garantir que apenas um arquivo seja enviado
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
    public ResponseEntity<Livro> buscarLivro(@PathVariable UUID id) {
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping // Rota pública
    public ResponseEntity<List<Livro>> listarLivros() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = livroService.listarDisponiveis();
        return ResponseEntity.ok(livrosDisponiveis);
    }

    @PutMapping("/{id}") // Rota protegida - ADMIN ou COMUM
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable UUID id, @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok(livroService.atualizarLivro(id, livroDTO));
    }

    @DeleteMapping("/{id}") // Rota protegida - apenas ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarLivro(@PathVariable UUID id) {
        livroService.deletarLivro(id);
        return ResponseEntity.ok().build();
    }
}
