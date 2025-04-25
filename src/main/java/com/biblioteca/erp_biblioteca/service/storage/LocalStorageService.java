package com.biblioteca.erp_biblioteca.service.storage;

import com.biblioteca.erp_biblioteca.config.StorageConfig;
import com.biblioteca.erp_biblioteca.exception.StorageException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    // Dimensões padrão para capas de livros (proporção 2:3)
    private static final int THUMB_WIDTH = 400;
    private static final int THUMB_HEIGHT = 600;
    private static final int QUALITY = 90;

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

            Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                .normalize()
                .toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Não é possível armazenar arquivo fora do diretório atual.");
            }

            // Lê a imagem original
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            
            // Cria versão otimizada para capa
            processAndSaveBookCover(originalImage, destinationFile.toFile());

            return filename;
        } catch (IOException e) {
            throw new StorageException("Falha ao armazenar arquivo " + filename, e);
        }
    }

    private void processAndSaveBookCover(BufferedImage originalImage, File destinationFile) throws IOException {
        // Calcula as dimensões mantendo a proporção 2:3
        double aspectRatio = (double) THUMB_HEIGHT / THUMB_WIDTH;
        int targetWidth = THUMB_WIDTH;
        int targetHeight = THUMB_HEIGHT;

        // Recorta a imagem para se ajustar à proporção 2:3 se necessário
        BufferedImage croppedImage = cropToAspectRatio(originalImage, aspectRatio);

        // Processa e salva a imagem
        Thumbnails.of(croppedImage)
            .size(targetWidth, targetHeight)
            .outputQuality(QUALITY / 100.0)
            .outputFormat("jpg")  // Padroniza formato como JPG
            .toFile(destinationFile);
    }

    private BufferedImage cropToAspectRatio(BufferedImage original, double targetRatio) throws IOException {
        double originalRatio = (double) original.getHeight() / original.getWidth();
        
        if (Math.abs(originalRatio - targetRatio) < 0.01) {
            return original;
        }

        int cropWidth, cropHeight;
        if (originalRatio > targetRatio) {
            // Imagem muito alta - cortar altura
            cropWidth = original.getWidth();
            cropHeight = (int) (cropWidth * targetRatio);
        } else {
            // Imagem muito larga - cortar largura
            cropHeight = original.getHeight();
            cropWidth = (int) (cropHeight / targetRatio);
        }

        return Thumbnails.of(original)
            .sourceRegion(Positions.CENTER, cropWidth, cropHeight)
            .size(cropWidth, cropHeight)
            .asBufferedImage();
    }

    private void validateFile(MultipartFile file) {
        long fileSize = file.getSize();
        String contentType = file.getContentType();
        
        log.info("Validando arquivo: tamanho={}, tipo={}, maxPermitido={}", 
            fileSize, 
            contentType, 
            storageConfig.getMaxFileSize());
        
        if (fileSize > storageConfig.getMaxFileSize()) {
            throw new StorageException(String.format(
                "Arquivo excede o tamanho máximo permitido. Tamanho: %d bytes, Máximo: %d bytes", 
                fileSize, 
                storageConfig.getMaxFileSize()));
        }

        if (!storageConfig.getAllowedContentTypes().contains(contentType)) {
            throw new StorageException(String.format(
                "Tipo de arquivo não permitido: %s. Tipos permitidos: %s", 
                contentType, 
                String.join(", ", storageConfig.getAllowedContentTypes())));
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s.jpg", timestamp, random); // Sempre .jpg
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Não foi possível ler o arquivo: " + filename);
            }
        } catch (Exception e) {
            throw new StorageException("Não foi possível ler o arquivo: " + filename, e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Não foi possível deletar o arquivo: " + filename, e);
        }
    }

    @Override
    public String getFileUrl(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/images/")
            .path(filename)
            .toUriString();
    }
}