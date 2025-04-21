package com.biblioteca.erp_biblioteca.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageConfig {
    private String uploadDir;
    private long maxFileSize; // em bytes
    private List<String> allowedContentTypes = new ArrayList<>(); // Inicialização da lista
    private int imageMaxWidth;
    private int imageMaxHeight;

    @Bean
    public Path rootLocation() {
        return Paths.get(uploadDir);
    }

    // Método explícito para garantir que nunca retorne null
    public List<String> getAllowedContentTypes() {
        return allowedContentTypes != null ? allowedContentTypes : new ArrayList<>();
    }
}
