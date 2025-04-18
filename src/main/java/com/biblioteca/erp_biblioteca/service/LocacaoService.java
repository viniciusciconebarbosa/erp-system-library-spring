package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.enums.StatusLocacao;
import com.biblioteca.erp_biblioteca.model.Locacao;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.repository.LocacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;

    public Locacao criarLocacao(Usuario usuario, Livro livro) {
        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        locacao.setLivro(livro);
        locacao.setDataLocacao(new Date());
        return locacaoRepository.save(locacao);
    }

    public void finalizarLocacao(UUID id) {
        Locacao locacao = locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
        locacao.setDataDevolucao(new Date());
        locacao.setStatus(StatusLocacao.FINALIZADA);
        locacaoRepository.save(locacao);
    }

    public void marcarLocacaoComoAtrasada(UUID id) {
        Locacao locacao = locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
        locacao.setStatus(StatusLocacao.ATRASADA);
        locacaoRepository.save(locacao);
    }

    public void cancelarLocacao(UUID id) {
        Locacao locacao = locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
        locacao.setStatus(StatusLocacao.CANCELADA);
        locacaoRepository.save(locacao);
    }

    public Locacao buscarLocacao(UUID id) {
        return locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
    }
}
