package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.DeleteResponse;
import com.biblioteca.erp_biblioteca.dto.LocacaoDTO;
import com.biblioteca.erp_biblioteca.model.Locacao;
import com.biblioteca.erp_biblioteca.service.LocacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locacoes")
@RequiredArgsConstructor
@Tag(name = "Locações", description = "API para gerenciamento de locações de livros da biblioteca")
public class LocacaoController {
    private final LocacaoService locacaoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Cria uma nova locação",
        description = "Registra uma nova locação de livro para um usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM"),
        @ApiResponse(responseCode = "404", description = "Livro ou usuário não encontrado"),
        @ApiResponse(responseCode = "409", description = "Livro não disponível para locação")
    })
    public ResponseEntity<Locacao> criarLocacao(
        @Parameter(description = "Dados da locação")
        @Valid @RequestBody LocacaoDTO locacaoDTO) {
        Locacao novaLocacao = locacaoService.criarLocacao(locacaoDTO);
        return ResponseEntity.ok(novaLocacao);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Busca uma locação específica",
        description = "Retorna os detalhes completos de uma locação específica baseado em seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação encontrada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada")
    })
    public ResponseEntity<Locacao> buscarLocacao(
        @Parameter(description = "ID da locação", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        Locacao locacao = locacaoService.buscarLocacao(id);
        return ResponseEntity.ok(locacao);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Lista todas as locações",
        description = "Retorna uma lista com todas as locações registradas no sistema. Apenas administradores têm acesso"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de locações retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN")
    })
    public ResponseEntity<List<Locacao>> listarLocacoes() {
        List<Locacao> locacoes = locacaoService.listarTodas();
        return ResponseEntity.ok(locacoes);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Lista locações de um usuário",
        description = "Retorna todas as locações associadas a um usuário específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de locações do usuário retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<List<Locacao>> listarLocacoesPorUsuario(
        @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID usuarioId) {
        List<Locacao> locacoes = locacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(locacoes);
    }

    @PutMapping("/{id}/devolver")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    @Operation(
        summary = "Registra devolução de livro",
        description = "Registra a devolução de um livro, finalizando a locação ativa"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolução registrada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN ou COMUM"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada"),
        @ApiResponse(responseCode = "409", description = "Locação já finalizada ou cancelada")
    })
    public ResponseEntity<Locacao> devolverLivro(
        @Parameter(description = "ID da locação", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        Locacao locacao = locacaoService.registrarDevolucao(id);
        return ResponseEntity.ok(locacao);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Cancela uma locação",
        description = "Permite cancelar uma locação existente. Apenas administradores podem realizar esta operação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Locação cancelada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado - necessário autenticação"),
        @ApiResponse(responseCode = "403", description = "Proibido - necessário permissão ADMIN"),
        @ApiResponse(responseCode = "404", description = "Locação não encontrada"),
        @ApiResponse(responseCode = "409", description = "Locação já finalizada ou cancelada")
    })
    public ResponseEntity<DeleteResponse> cancelarLocacao(
        @Parameter(description = "ID da locação", example = "123e4567-e89b-12d3-a456-426614174000")
        @PathVariable UUID id) {
        locacaoService.cancelarLocacao(id);
        DeleteResponse response = new DeleteResponse(
            "Locação cancelada com sucesso",
            id.toString(),
            "Locacao"
        );
        return ResponseEntity.ok(response);
    }
}
