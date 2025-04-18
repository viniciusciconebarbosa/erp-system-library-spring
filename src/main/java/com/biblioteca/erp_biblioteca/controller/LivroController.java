package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {
    private final LivroService livroService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COMUM')")
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        Livro novoLivro = livroService.cadastrarLivro(livroDTO);
        return ResponseEntity.ok(novoLivro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivro(@PathVariable UUID id) {
        Livro livro = livroService.buscarLivro(id);
        return ResponseEntity.ok(livro);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarTodos();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = livroService.listarDisponiveis();
        return ResponseEntity.ok(livrosDisponiveis);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Livro> atualizarLivro(
            @PathVariable UUID id,
            @RequestBody LivroDTO livroDTO) {
        Livro livroAtualizado = livroService.atualizarLivro(id, livroDTO);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarLivro(@PathVariable UUID id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}