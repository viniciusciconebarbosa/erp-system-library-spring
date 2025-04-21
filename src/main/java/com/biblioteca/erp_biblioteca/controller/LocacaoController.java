package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.LocacaoDTO;
import com.biblioteca.erp_biblioteca.model.Locacao;
import com.biblioteca.erp_biblioteca.service.LocacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locacoes")
@RequiredArgsConstructor
public class LocacaoController {
    private final LocacaoService locacaoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Locacao> criarLocacao(@Valid @RequestBody LocacaoDTO locacaoDTO) {
        Locacao novaLocacao = locacaoService.criarLocacao(locacaoDTO);
        return ResponseEntity.ok(novaLocacao);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Locacao> buscarLocacao(@PathVariable UUID id) {
        Locacao locacao = locacaoService.buscarLocacao(id);
        return ResponseEntity.ok(locacao);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Locacao>> listarLocacoes() {
        List<Locacao> locacoes = locacaoService.listarTodas();
        return ResponseEntity.ok(locacoes);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<List<Locacao>> listarLocacoesPorUsuario(@PathVariable UUID usuarioId) {
        List<Locacao> locacoes = locacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(locacoes);
    }

    @PutMapping("/{id}/devolver")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Locacao> devolverLivro(@PathVariable UUID id) {
        Locacao locacao = locacaoService.registrarDevolucao(id);
        return ResponseEntity.ok(locacao);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancelarLocacao(@PathVariable UUID id) {
        locacaoService.cancelarLocacao(id);
        return ResponseEntity.noContent().build();
    }
}
