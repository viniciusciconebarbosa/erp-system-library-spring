package com.biblioteca.erp_biblioteca.controller;

import com.biblioteca.erp_biblioteca.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final StorageService storageService;

    @GetMapping(value = "/{filename:.+}", produces = "image/jpeg")
    public Resource serveFile(@PathVariable String filename) {
        return storageService.loadAsResource(filename);
    }
}
