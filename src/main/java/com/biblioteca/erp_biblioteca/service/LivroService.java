package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.exception.BusinessException;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.biblioteca.erp_biblioteca.service.storage.StorageService;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final StorageService storageService;

    @Transactional
    public Livro cadastrarLivro(LivroDTO livroDTO, MultipartFile capaFile) {
        // Processa o arquivo de imagem se fornecido
        String filename = null;
        if (capaFile != null && !capaFile.isEmpty()) {
            filename = storageService.store(capaFile);
        }

        // Busca o doador se o ID foi fornecido
        Usuario doador = null;
        if (livroDTO.getDoadorId() != null) {
            doador = usuarioRepository.findById(livroDTO.getDoadorId())
                .orElseThrow(() -> new RuntimeException("ID do doador informado não existe"));
        }

        // Cria e salva o livro
        Livro livro = Livro.builder()
            .titulo(livroDTO.getTitulo())
            .genero(livroDTO.getGenero())
            .capaFoto(filename != null ? storageService.getFileUrl(filename) : null)
            .classificacaoEtaria(livroDTO.getClassificacaoEtaria())
            .estadoConservacao(livroDTO.getEstadoConservacao())
            .doador(doador)
            .disponivelLocacao(true)
            .build();

        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivelLocacaoTrue();
    }

    public Livro atualizarLivro(UUID id, LivroDTO livroDTO) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Usuario doador = null;
        if (livroDTO.getDoadorId() != null) {
            doador = usuarioRepository.findById(livroDTO.getDoadorId())
                .orElseThrow(() -> new RuntimeException("ID do doador informado não existe"));
        }

        livro.setTitulo(livroDTO.getTitulo());
        livro.setGenero(livroDTO.getGenero());
        livro.setCapaFoto(livroDTO.getCapaFoto());
        livro.setClassificacaoEtaria(livroDTO.getClassificacaoEtaria());
        livro.setEstadoConservacao(livroDTO.getEstadoConservacao());
        livro.setDoador(doador);

        return livroRepository.save(livro);
    }

    public Livro buscarLivro(UUID id) {
        return livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    @Transactional
    public void deletarLivro(UUID id) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new BusinessException("Livro não encontrado"));

        if (livro.getCapaFoto() != null) {
            String filename = livro.getCapaFoto().substring(livro.getCapaFoto().lastIndexOf("/") + 1);
            storageService.delete(filename);
        }

        livroRepository.deleteById(id);
    }
}