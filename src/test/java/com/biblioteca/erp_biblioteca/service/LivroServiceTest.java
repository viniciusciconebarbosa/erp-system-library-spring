package com.biblioteca.erp_biblioteca.service;

import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.repository.LivroRepository;
import com.biblioteca.erp_biblioteca.repository.UsuarioRepository;
import com.biblioteca.erp_biblioteca.service.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private LivroService livroService;

    private LivroDTO livroDTO;
    private Usuario doador;
    private MultipartFile capaFile;

    @BeforeEach
    void setUp() {
        doador = new Usuario();
        doador.setId(UUID.randomUUID());
        
        livroDTO = new LivroDTO();
        livroDTO.setTitulo("O Senhor dos Anéis");
        livroDTO.setDoadorId(doador.getId());
        livroDTO.setGenero(Genero.FICCAO);
        livroDTO.setClassificacaoEtaria(ClassificacaoEtaria.LIVRE);
        livroDTO.setEstadoConservacao(EstadoConservacao.BOM);

        // Mock do MultipartFile
        capaFile = new MockMultipartFile(
            "capa",
            "capa.jpg",
            "image/jpeg",
            "test image content".getBytes()
        );
    }

    @Test
    void deveCadastrarLivroComSucesso() {
        // Given
        when(usuarioRepository.findById(livroDTO.getDoadorId())).thenReturn(Optional.of(doador));
        when(storageService.store(any())).thenReturn("capa.jpg");
        when(livroRepository.save(any())).thenAnswer(i -> {
            Livro l = (Livro) i.getArguments()[0];
            l.setId(UUID.randomUUID());
            return l;
        });

        // When
        Livro livro = livroService.cadastrarLivro(livroDTO, capaFile);

        // Then
        assertNotNull(livro.getId());
        assertEquals(livroDTO.getTitulo(), livro.getTitulo());
        assertEquals(doador, livro.getDoador());
        assertTrue(livro.isDisponivelLocacao());
        assertEquals("capa.jpg", livro.getCapaFoto()); // Removida a parte do caminho
    }

    @Test
    void deveCadastrarLivroSemCapa() {
        // Given
        when(usuarioRepository.findById(livroDTO.getDoadorId())).thenReturn(Optional.of(doador));
        when(livroRepository.save(any())).thenAnswer(i -> {
            Livro l = (Livro) i.getArguments()[0];
            l.setId(UUID.randomUUID());
            return l;
        });

        // When
        Livro livro = livroService.cadastrarLivro(livroDTO, null);

        // Then
        assertNotNull(livro.getId());
        assertEquals(livroDTO.getTitulo(), livro.getTitulo());
        assertEquals(doador, livro.getDoador());
        assertTrue(livro.isDisponivelLocacao());
        assertNull(livro.getCapaFoto());
    }

    @Test
    void deveListarTodosOsLivros() {
        Livro livro1 = new Livro();
        livro1.setId(UUID.randomUUID());
        livro1.setTitulo("Livro 1");

        Livro livro2 = new Livro();
        livro2.setId(UUID.randomUUID());
        livro2.setTitulo("Livro 2");

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<Livro> livros = livroService.listarTodos();

        assertEquals(2, livros.size());
        assertEquals("Livro 1", livros.get(0).getTitulo());
        assertEquals("Livro 2", livros.get(1).getTitulo());
    }
}