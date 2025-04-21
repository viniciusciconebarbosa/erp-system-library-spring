package com.biblioteca.erp_biblioteca.service.storage;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.exception.StorageException;
import com.biblioteca.erp_biblioteca.exception.StorageFileNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {
    private final StorageConfig storageConfig;
    private final Path rootLocation;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Não foi possível inicializar o local de armazenamento", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        validateFile(file);
        
        String filename = generateUniqueFilename(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Arquivo vazio: " + filename);
            }

            // Normaliza e verifica o path para evitar directory traversal
            Path destinationFile = this.rootLocation.resolve(
                Paths.get(filename))
                .normalize()
                .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Não é possível armazenar arquivo fora do diretório atual.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                BufferedImage originalImage = ImageIO.read(inputStream);
                BufferedImage resizedImage = resizeImage(originalImage);
                
                String extension = FilenameUtils.getExtension(filename);
                ImageIO.write(resizedImage, extension, destinationFile.toFile());
            }

            return filename;
        } catch (IOException e) {
            throw new StorageException("Falha ao armazenar arquivo " + filename, e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Não foi possível ler arquivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Não foi possível ler arquivo: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Erro ao deletar arquivo " + filename, e);
        }
    }

    @Override
    public String getFileUrl(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/images/")
            .path(filename)
            .toUriString();
    }

    private void validateFile(MultipartFile file) {
        // Validar tamanho
        if (file.getSize() > storageConfig.getMaxFileSize()) {
            throw new StorageException("Arquivo excede o tamanho máximo permitido");
        }

        // Validar tipo de conteúdo
        String contentType = file.getContentType();
        if (!storageConfig.getAllowedContentTypes().contains(contentType)) {
            throw new StorageException("Tipo de arquivo não permitido");
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s.%s", timestamp, random, extension);
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        int targetWidth = Math.min(originalImage.getWidth(), storageConfig.getImageMaxWidth());
        int targetHeight = Math.min(originalImage.getHeight(), storageConfig.getImageMaxHeight());

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }
}
