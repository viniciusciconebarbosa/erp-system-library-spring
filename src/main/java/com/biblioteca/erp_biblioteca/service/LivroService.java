package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public Livro cadastrarLivro(LivroDTO livroDTO) {
        Usuario doador = null;
        if (livroDTO.getDoadorId() != null) {
            doador = usuarioRepository.findById(livroDTO.getDoadorId())
                .orElseThrow(() -> new RuntimeException("ID do doador informado não existe"));
        }

        Livro livro = Livro.builder()
            .titulo(livroDTO.getTitulo())
            .genero(livroDTO.getGenero())
            .capaFoto(livroDTO.getCapaFoto())
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

    public void deletarLivro(UUID id) {
        livroRepository.deleteById(id);
    }
}