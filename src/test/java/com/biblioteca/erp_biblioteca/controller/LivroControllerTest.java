package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.dto.LivroDTO;
import com.biblioteca.erp_biblioteca.dto.LivroResumoDTO;
import com.biblioteca.erp_biblioteca.enums.ClassificacaoEtaria;
import com.biblioteca.erp_biblioteca.enums.EstadoConservacao;
import com.biblioteca.erp_biblioteca.enums.Genero;
import com.biblioteca.erp_biblioteca.model.Livro;
import com.biblioteca.erp_biblioteca.model.Usuario;
import com.biblioteca.erp_biblioteca.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LivroService livroService;

    private LivroDTO livroDTO;
    private Livro livro;
    private final UUID livroId = UUID.randomUUID();
    private final UUID doadorId = UUID.randomUUID();
    private MockMultipartFile capaFile;

    @BeforeEach
    void setUp() {
        Usuario doador = Usuario.builder()
                .id(doadorId)
                .nome("Doador Teste")
                .email("doador@example.com")
                .build();

        livroDTO = LivroDTO.builder()
                .titulo("O Senhor dos Anéis")
                .autor("J.R.R. Tolkien")
                .genero(Genero.FICCAO)
                .classificacaoEtaria(ClassificacaoEtaria.DOZE_ANOS)
                .estadoConservacao(EstadoConservacao.BOM)
                .doadorId(doadorId)
                .sinopse("Uma aventura épica na Terra-média")
                .build();

        livro = Livro.builder()
                .id(livroId)
                .titulo(livroDTO.getTitulo())
                .autor(livroDTO.getAutor())
                .genero(livroDTO.getGenero())
                .capaFoto("capa.jpg")
                .disponivelLocacao(true)
                .classificacaoEtaria(livroDTO.getClassificacaoEtaria())
                .estadoConservacao(livroDTO.getEstadoConservacao())
                .sinopse(livroDTO.getSinopse())
                .doador(doador)
                .build();

        capaFile = new MockMultipartFile(
            "capa",
            "capa.jpg",
            "image/jpeg",
            "test image content".getBytes()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveCadastrarLivroComSucesso() throws Exception {
        when(livroService.cadastrarLivro(any(LivroDTO.class), any())).thenReturn(livro);

        MockMultipartFile livroJson = new MockMultipartFile(
            "livro",
            "",
            "application/json",
            objectMapper.writeValueAsString(livroDTO).getBytes()
        );

        mockMvc.perform(multipart("/api/livros")
                .file(capaFile)
                .file(livroJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Livro cadastrado com sucesso"))
                .andExpect(jsonPath("$.data.titulo").value(livroDTO.getTitulo()))
                .andExpect(jsonPath("$.data.autor").value(livroDTO.getAutor()));
    }

    @Test
    void naoDeveCadastrarLivroSemAutenticacao() throws Exception {
        MockMultipartFile livroJson = new MockMultipartFile(
            "livro",
            "",
            "application/json",
            objectMapper.writeValueAsString(livroDTO).getBytes()
        );

        mockMvc.perform(multipart("/api/livros")
                .file(capaFile)
                .file(livroJson))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message").value("Acesso negado - Permissão insuficiente"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveBuscarLivroComSucesso() throws Exception {
        when(livroService.buscarLivro(livroId)).thenReturn(livro);

        mockMvc.perform(get("/api/livros/" + livroId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(livroId.toString()))
                .andExpect(jsonPath("$.titulo").value(livroDTO.getTitulo()))
                .andExpect(jsonPath("$.autor").value(livroDTO.getAutor()));
    }

    @Test
    void deveListarLivrosDisponiveis() throws Exception {
        Livro outroLivro = Livro.builder()
                .id(UUID.randomUUID())
                .titulo("Harry Potter")
                .autor("J.K. Rowling")
                .genero(Genero.FICCAO)
                .disponivelLocacao(true)
                .build();

        LivroResumoDTO livroResumo1 = new LivroResumoDTO(
            livro.getId(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getCapaFoto(),
            livro.isDisponivelLocacao()
        );

        LivroResumoDTO livroResumo2 = new LivroResumoDTO(
            outroLivro.getId(),
            outroLivro.getTitulo(),
            outroLivro.getAutor(),
            outroLivro.getCapaFoto(),
            outroLivro.isDisponivelLocacao()
        );

        when(livroService.listarTodosResumido()).thenReturn(Arrays.asList(livroResumo1, livroResumo2));

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(livro.getTitulo()))
                .andExpect(jsonPath("$[1].titulo").value(outroLivro.getTitulo()));
    }

    @Test
    @WithMockUser(roles = "COMUM")
    void naoDevePermitirAtualizacaoDeLivroPorUsuarioComum() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(livroDTO);
        System.out.println("Request JSON: " + jsonRequest);

        mockMvc.perform(put("/api/livros/" + livroId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Acesso negado - Permissão insuficiente"))
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void devePermitirAtualizacaoDeLivroPorAdmin() throws Exception {
        when(livroService.atualizarLivro(any(UUID.class), any(LivroDTO.class))).thenReturn(livro);

        String jsonRequest = objectMapper.writeValueAsString(livroDTO);
        System.out.println("Request JSON: " + jsonRequest);

        mockMvc.perform(put("/api/livros/" + livroId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Livro atualizado com sucesso"))
                .andExpect(jsonPath("$.data.titulo").value(livroDTO.getTitulo()))
                .andExpect(jsonPath("$.data.autor").value(livroDTO.getAutor()))
                .andExpect(jsonPath("$.data.genero").value(livroDTO.getGenero().toString()))
                .andExpect(jsonPath("$.data.classificacaoEtaria").value(livroDTO.getClassificacaoEtaria().toString()))
                .andExpect(jsonPath("$.data.estadoConservacao").value(livroDTO.getEstadoConservacao().toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveAtualizarLivroComSucesso() throws Exception {
        // Criando um DTO válido para atualização
        LivroDTO livroValido = LivroDTO.builder()
                .titulo("O Senhor dos Anéis - Atualizado")
                .autor("J.R.R. Tolkien")
                .genero(Genero.FICCAO)
                .classificacaoEtaria(ClassificacaoEtaria.DOZE_ANOS)
                .estadoConservacao(EstadoConservacao.BOM)
                .sinopse("Uma aventura épica na Terra-média")
                .build();

        // Criando o livro que será retornado pelo serviço
        Livro livroAtualizado = Livro.builder()
                .id(livroId)
                .titulo(livroValido.getTitulo())
                .autor(livroValido.getAutor())
                .genero(livroValido.getGenero())
                .classificacaoEtaria(livroValido.getClassificacaoEtaria())
                .estadoConservacao(livroValido.getEstadoConservacao())
                .sinopse(livroValido.getSinopse())
                .disponivelLocacao(true)
                .build();

        when(livroService.atualizarLivro(eq(livroId), any(LivroDTO.class))).thenReturn(livroAtualizado);

        String jsonRequest = objectMapper.writeValueAsString(livroValido);
        System.out.println("Request JSON para atualização: " + jsonRequest);

        mockMvc.perform(put("/api/livros/" + livroId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Livro atualizado com sucesso"))
                .andExpect(jsonPath("$.data.titulo").value(livroValido.getTitulo()))
                .andExpect(jsonPath("$.data.autor").value(livroValido.getAutor()))
                .andExpect(jsonPath("$.data.genero").value(livroValido.getGenero().toString()))
                .andExpect(jsonPath("$.data.classificacaoEtaria").value(livroValido.getClassificacaoEtaria().toString()))
                .andExpect(jsonPath("$.data.estadoConservacao").value(livroValido.getEstadoConservacao().toString()))
                .andExpect(jsonPath("$.data.sinopse").value(livroValido.getSinopse()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveDeletarLivroComSucesso() throws Exception {
        mockMvc.perform(delete("/api/livros/" + livroId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Livro deletado com sucesso"))
                .andExpect(jsonPath("$.id").value(livroId.toString()))
                .andExpect(jsonPath("$.type").value("SUCCESS"));
    }

    @Test
    @WithMockUser(roles = "COMUM")
    void naoDevePermitirDelecaoDeLivroPorUsuarioComum() throws Exception {
        mockMvc.perform(delete("/api/livros/" + livroId))
                .andExpect(status().isForbidden());
    }
} 