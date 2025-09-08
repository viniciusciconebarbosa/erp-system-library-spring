package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.service.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ImageController imageController;

    @Test
    void deveServirArquivoComSucesso() {
        // Arrange
        String filename = "test.jpg";
        Resource resource = new ByteArrayResource("conteudo da imagem".getBytes());
        when(storageService.loadAsResource(filename)).thenReturn(resource);

        // Act
        ResponseEntity<Resource> response = imageController.serveFile(filename);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        assertEquals(resource, response.getBody());
        verify(storageService).loadAsResource(filename);
    }

    @Test
    void deveLancarExcecaoParaArquivoNaoEncontrado() {
        // Arrange
        String filename = "arquivo_inexistente.jpg";
        when(storageService.loadAsResource(filename))
            .thenThrow(new RuntimeException("Arquivo não encontrado"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            imageController.serveFile(filename);
        });
        verify(storageService).loadAsResource(filename);
    }

    @Test
    void deveDefinirContentTypeCorreto() {
        // Arrange
        String filename = "imagem.jpg";
        Resource resource = new ByteArrayResource("dados da imagem".getBytes());
        when(storageService.loadAsResource(filename)).thenReturn(resource);

        // Act
        ResponseEntity<Resource> response = imageController.serveFile(filename);

        // Assert
        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
    }

    @Test
    void deveRetornarResourceCorreto() {
        // Arrange
        String filename = "capa_livro.jpg";
        Resource expectedResource = new ByteArrayResource("conteudo".getBytes());
        when(storageService.loadAsResource(filename)).thenReturn(expectedResource);

        // Act
        ResponseEntity<Resource> response = imageController.serveFile(filename);

        // Assert
        assertEquals(expectedResource, response.getBody());
    }
}
