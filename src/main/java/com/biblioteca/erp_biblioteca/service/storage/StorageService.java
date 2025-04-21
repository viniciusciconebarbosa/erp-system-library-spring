package com.biblioteca.erp_biblioteca.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file);
    Resource loadAsResource(String filename);  // org.springframework.core.io.Resource
    void delete(String filename);
    String getFileUrl(String filename);
}