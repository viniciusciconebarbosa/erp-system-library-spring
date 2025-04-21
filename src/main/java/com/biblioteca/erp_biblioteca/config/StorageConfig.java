package com.biblioteca.erp_biblioteca.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageConfig {
    private String uploadDir;
    private long maxFileSize; // em bytes
    private List<String> allowedContentTypes;
    private int imageMaxWidth;
    private int imageMaxHeight;
}