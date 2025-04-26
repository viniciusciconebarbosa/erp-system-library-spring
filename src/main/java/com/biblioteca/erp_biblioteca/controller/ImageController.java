package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequiredArgsConstructor
@RestController
@Tag(name = "Imagens", description = "API para acesso a imagens armazenadas")
public class ImageController {
    private final StorageService storageService;
    
    @GetMapping("/images/{filename}")
    @Operation(
        summary = "Recupera uma imagem",
        description = "Retorna uma imagem armazenada no sistema pelo seu nome de arquivo. Todas as imagens são servidas como JPG em dimensões 400x600."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada")
    })
    public ResponseEntity<Resource> serveFile(
        @Parameter(description = "Nome do arquivo da imagem", example = "20240426123456_abc123.jpg")
        @PathVariable String filename) {
        
        Resource file = storageService.loadAsResource(filename);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
            .body(file);
    }
}

