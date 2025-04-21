package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LocacaoDTO;
import com.biblioteca.erp_biblioteca.enums.StatusLocacao;
import com.biblioteca.erp_biblioteca.exception.BusinessException;
import com.biblioteca.erp_biblioteca.model.Locacao;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.repository.LocacaoRepository;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocacaoService {
    private final LocacaoRepository locacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    @Transactional
    public Locacao criarLocacao(LocacaoDTO locacaoDTO) {
        Usuario usuario = usuarioRepository.findById(locacaoDTO.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Livro livro = livroRepository.findById(locacaoDTO.getLivroId())
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        if (!livro.isDisponivelLocacao()) {
            throw new RuntimeException("Livro não está disponível para locação");
        }

        livro.setDisponivelLocacao(false);
        livroRepository.save(livro);

        Locacao locacao = Locacao.builder()
            .usuario(usuario)
            .livro(livro)
            .dataLocacao(new Date())
            .status(StatusLocacao.ATIVA)
            .build();

        return locacaoRepository.save(locacao);
    }

    public Locacao buscarLocacao(UUID id) {
        return locacaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Locação não encontrada"));
    }

    public List<Locacao> listarTodas() {
        return locacaoRepository.findAll();
    }

    public List<Locacao> listarPorUsuario(UUID usuarioId) {
        return locacaoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Locacao registrarDevolucao(UUID id) {
        Locacao locacao = buscarLocacao(id);
        
        if (locacao.getStatus() != StatusLocacao.ATIVA) {
            throw new RuntimeException("Locação não está ativa");
        }

        locacao.setDataDevolucao(new Date());
        locacao.setStatus(StatusLocacao.FINALIZADA);
        
        Livro livro = locacao.getLivro();
        livro.setDisponivelLocacao(true);
        livroRepository.save(livro);

        return locacaoRepository.save(locacao);
    }

    @Transactional
    public void cancelarLocacao(UUID id) {
        if (!locacaoRepository.existsById(id)) {
            throw new BusinessException("Locação não encontrada");
        }
        locacaoRepository.deleteById(id);
    }
}
