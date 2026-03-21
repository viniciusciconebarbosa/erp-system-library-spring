package com.biblioteca.erp_biblioteca.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

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
    private long maxFileSize;
    private List<String> allowedContentTypes = new ArrayList<>();
    private int imageMaxWidth;
    private int imageMaxHeight;

    @Value("${app.api-base-url:http://localhost:8080}")
    private String apiBaseUrl;

    public String getBaseImageUrl() {
        return apiBaseUrl;
    }
    @Bean
    public Path rootLocation() {
        return Paths.get(uploadDir);
    }

    public String getFullImageUrl(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        String normalizedPath = imagePath.replace("/uploads/capas/", "");

        normalizedPath = normalizedPath.startsWith("/") ? normalizedPath.substring(1) : normalizedPath;
        return getBaseImageUrl() + "/images/" + normalizedPath;
    }
}
