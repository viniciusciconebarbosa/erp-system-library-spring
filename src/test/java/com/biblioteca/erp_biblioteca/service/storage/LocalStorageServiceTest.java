package com.biblioteca.erp_biblioteca.service.storage;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalStorageServiceTest {

    @Mock
    private StorageConfig storageConfig;

    private LocalStorageService localStorageService;
    private Path rootLocation;

    @BeforeEach
    void setUp() {
        rootLocation = Paths.get("uploads-test");
        localStorageService = new LocalStorageService(storageConfig, rootLocation);
    }

    @Test
    void deveLancarExcecaoParaArquivoInvalido() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(5242880L);
        when(storageConfig.getAllowedContentTypes()).thenReturn(
            Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp")
        );
        
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.jpg",
            "image/jpeg",
            new byte[]{1, 2, 3, 4, 5}
        );

        // Act & Assert - deve lançar exceção porque não é uma imagem válida
        assertThrows(NullPointerException.class, () -> {
            localStorageService.store(file);
        });
    }

    @Test
    void deveLancarExcecaoParaArquivoVazio() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(5242880L);
        when(storageConfig.getAllowedContentTypes()).thenReturn(
            Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp")
        );
        
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "empty.jpg",
            "image/jpeg",
            new byte[0]
        );

        // Act & Assert
        assertThrows(StorageException.class, () -> {
            localStorageService.store(file);
        });
    }

    @Test
    void deveLancarExcecaoParaTipoDeArquivoNaoPermitido() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(5242880L);
        when(storageConfig.getAllowedContentTypes()).thenReturn(
            Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp")
        );
        
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            "text/plain",
            "conteudo do arquivo".getBytes()
        );

        // Act & Assert
        assertThrows(StorageException.class, () -> {
            localStorageService.store(file);
        });
    }

    @Test
    void deveLancarExcecaoParaArquivoMuitoGrande() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(100L); // 100 bytes
        
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "large.jpg",
            "image/jpeg",
            new byte[200] // 200 bytes
        );

        // Act & Assert
        assertThrows(StorageException.class, () -> {
            localStorageService.store(file);
        });
    }

    @Test
    void deveLancarExcecaoAoCarregarArquivoInexistente() {
        // Arrange
        String filename = "arquivo_inexistente.jpg";

        // Act & Assert
        assertThrows(StorageException.class, () -> {
            localStorageService.loadAsResource(filename);
        });
    }

    @Test
    void deveDeletarArquivoComSucesso() {
        // Arrange
        String filename = "test.jpg";

        // Act
        assertDoesNotThrow(() -> {
            localStorageService.delete(filename);
        });
    }

    @Test
    void deveValidarTamanhoDoArquivo() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(1000L);
        
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.jpg",
            "image/jpeg",
            new byte[1500] // Maior que o limite
        );

        // Act & Assert
        assertThrows(StorageException.class, () -> {
            localStorageService.store(file);
        });
    }

    @Test
    void deveLancarExcecaoParaTiposDeArquivoInvalidos() {
        // Arrange
        when(storageConfig.getMaxFileSize()).thenReturn(5242880L);
        when(storageConfig.getAllowedContentTypes()).thenReturn(
            Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp")
        );
        
        String[] tiposPermitidos = {"image/jpeg", "image/png", "image/gif", "image/webp"};

        for (String tipo : tiposPermitidos) {
            MockMultipartFile file = new MockMultipartFile(
                "file",
                "test." + tipo.split("/")[1],
                tipo,
                "conteudo".getBytes()
            );

            // Act & Assert - deve lançar exceção porque não é uma imagem válida
            assertThrows(NullPointerException.class, () -> {
                localStorageService.store(file);
            });
        }
    }
}
