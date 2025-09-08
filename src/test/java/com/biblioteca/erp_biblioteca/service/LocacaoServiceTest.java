package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LocacaoDTO;
import com.biblioteca.erp_biblioteca.enums.StatusLocacao;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.model.Locacao;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.LocacaoRepository;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocacaoServiceTest {

    @Mock
    private LocacaoRepository locacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LocacaoService locacaoService;

    private LocacaoDTO locacaoDTO;
    private Usuario usuario;
    private Livro livro;

    @BeforeEach
    void setUp() {
        locacaoDTO = new LocacaoDTO();
        locacaoDTO.setUsuarioId(UUID.randomUUID());
        locacaoDTO.setLivroId(UUID.randomUUID());

        usuario = Usuario.builder()
                .id(UUID.randomUUID())
                .nome("João Silva")
                .email("joao@email.com")
                .build();

        livro = Livro.builder()
                .id(UUID.randomUUID())
                .titulo("Livro Teste")
                .disponivelLocacao(true)
                .build();
    }

    @Test
    void deveCriarLocacaoComSucesso() {
        // Arrange
        when(usuarioRepository.findById(locacaoDTO.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(locacaoDTO.getLivroId())).thenReturn(Optional.of(livro));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(locacaoRepository.save(any(Locacao.class))).thenAnswer(i -> {
            Locacao loc = (Locacao) i.getArguments()[0];
            loc.setId(UUID.randomUUID());
            return loc;
        });

        // Act
        Locacao resultado = locacaoService.criarLocacao(locacaoDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(livro, resultado.getLivro());
        assertEquals(StatusLocacao.ATIVA, resultado.getStatus());
        assertFalse(livro.isDisponivelLocacao());
        verify(locacaoRepository).save(any(Locacao.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(locacaoDTO.getUsuarioId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.criarLocacao(locacaoDTO);
        });
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontrado() {
        // Arrange
        when(usuarioRepository.findById(locacaoDTO.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(locacaoDTO.getLivroId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.criarLocacao(locacaoDTO);
        });
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoDisponivel() {
        // Arrange
        livro.setDisponivelLocacao(false);
        when(usuarioRepository.findById(locacaoDTO.getUsuarioId())).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(locacaoDTO.getLivroId())).thenReturn(Optional.of(livro));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.criarLocacao(locacaoDTO);
        });
    }

    @Test
    void deveBuscarLocacaoComSucesso() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        Locacao locacao = Locacao.builder()
                .id(locacaoId)
                .usuario(usuario)
                .livro(livro)
                .status(StatusLocacao.ATIVA)
                .build();
        when(locacaoRepository.findById(locacaoId)).thenReturn(Optional.of(locacao));

        // Act
        Locacao resultado = locacaoService.buscarLocacao(locacaoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(locacaoId, resultado.getId());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(livro, resultado.getLivro());
    }

    @Test
    void deveLancarExcecaoQuandoLocacaoNaoEncontrada() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        when(locacaoRepository.findById(locacaoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.buscarLocacao(locacaoId);
        });
    }

    @Test
    void deveListarTodasLocacoes() {
        // Arrange
        List<Locacao> locacoes = Arrays.asList(
                Locacao.builder().id(UUID.randomUUID()).build(),
                Locacao.builder().id(UUID.randomUUID()).build()
        );
        when(locacaoRepository.findAll()).thenReturn(locacoes);

        // Act
        List<Locacao> resultado = locacaoService.listarTodas();

        // Assert
        assertEquals(2, resultado.size());
        verify(locacaoRepository).findAll();
    }

    @Test
    void deveListarLocacoesPorUsuario() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        List<Locacao> locacoes = Arrays.asList(
                Locacao.builder().id(UUID.randomUUID()).build()
        );
        when(locacaoRepository.findByUsuarioId(usuarioId)).thenReturn(locacoes);

        // Act
        List<Locacao> resultado = locacaoService.listarPorUsuario(usuarioId);

        // Assert
        assertEquals(1, resultado.size());
        verify(locacaoRepository).findByUsuarioId(usuarioId);
    }

    @Test
    void deveRegistrarDevolucaoComSucesso() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        Locacao locacao = Locacao.builder()
                .id(locacaoId)
                .usuario(usuario)
                .livro(livro)
                .status(StatusLocacao.ATIVA)
                .build();
        when(locacaoRepository.findById(locacaoId)).thenReturn(Optional.of(locacao));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);
        when(locacaoRepository.save(any(Locacao.class))).thenReturn(locacao);

        // Act
        Locacao resultado = locacaoService.registrarDevolucao(locacaoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusLocacao.FINALIZADA, resultado.getStatus());
        assertTrue(livro.isDisponivelLocacao());
        assertNotNull(resultado.getDataDevolucao());
    }

    @Test
    void deveLancarExcecaoAoRegistrarDevolucaoDeLocacaoInativa() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        Locacao locacao = Locacao.builder()
                .id(locacaoId)
                .status(StatusLocacao.FINALIZADA)
                .build();
        when(locacaoRepository.findById(locacaoId)).thenReturn(Optional.of(locacao));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.registrarDevolucao(locacaoId);
        });
    }

    @Test
    void deveCancelarLocacaoComSucesso() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        when(locacaoRepository.existsById(locacaoId)).thenReturn(true);

        // Act
        locacaoService.cancelarLocacao(locacaoId);

        // Assert
        verify(locacaoRepository).deleteById(locacaoId);
    }

    @Test
    void deveLancarExcecaoAoCancelarLocacaoInexistente() {
        // Arrange
        UUID locacaoId = UUID.randomUUID();
        when(locacaoRepository.existsById(locacaoId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            locacaoService.cancelarLocacao(locacaoId);
        });
    }

    @Test
    void deveListarLocacoesAtivas() {
        // Arrange
        List<Locacao> locacoes = Arrays.asList(
                Locacao.builder().status(StatusLocacao.ATIVA).build()
        );
        when(locacaoRepository.findByStatus(StatusLocacao.ATIVA)).thenReturn(locacoes);

        // Act
        List<Locacao> resultado = locacaoService.listarLocacoesAtivas();

        // Assert
        assertEquals(1, resultado.size());
        verify(locacaoRepository).findByStatus(StatusLocacao.ATIVA);
    }

    @Test
    void deveContarLocacoesAtivas() {
        // Arrange
        when(locacaoRepository.countByStatus(StatusLocacao.ATIVA)).thenReturn(5L);

        // Act
        long resultado = locacaoService.contarLocacoesAtivas();

        // Assert
        assertEquals(5L, resultado);
        verify(locacaoRepository).countByStatus(StatusLocacao.ATIVA);
    }
}
