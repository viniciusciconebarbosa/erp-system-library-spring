package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;

    public Livro atualizarLivro(UUID id, String novoTitulo) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        livro.setTitulo(novoTitulo);
        return livroRepository.save(livro);
    }

    public Livro buscarLivro(UUID id) {
        return livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public void deletarLivro(UUID id) {
        livroRepository.deleteById(id);
    }
}
