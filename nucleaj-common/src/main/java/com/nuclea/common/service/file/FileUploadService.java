package com.nuclea.common.service.file;

import com.nuclea.common.service.marker.IScopedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * File upload service for managing file uploads.
 */
@Service
@Slf4j
public class FileUploadService implements IScopedService {

    @Value("${app.upload.directory:uploads}")
    private String uploadDirectory;

    @Value("${app.upload.max-file-size:10485760}") // 10MB default
    private long maxFileSize;

    /**
     * Upload file and return saved file path.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String newFilename = generateUniqueFilename(extension);

        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("File uploaded successfully: {}", newFilename);
        return filePath.toString();
    }

    /**
     * Upload file to specific directory.
     */
    public String uploadFile(MultipartFile file, String subdirectory) throws IOException {
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String newFilename = generateUniqueFilename(extension);

        Path uploadPath = Paths.get(uploadDirectory, subdirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("File uploaded successfully to {}: {}", subdirectory, newFilename);
        return filePath.toString();
    }

    /**
     * Delete file.
     */
    public boolean deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
            log.info("File deleted successfully: {}", filePath);
            return true;
        } catch (IOException e) {
            log.error("Error deleting file {}: {}", filePath, e.getMessage());
            return false;
        }
    }

    /**
     * Get file extension.
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    /**
     * Generate unique filename with timestamp and UUID.
     */
    private String generateUniqueFilename(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s%s", timestamp, uuid, extension);
    }

    /**
     * Validate file.
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException(
                    String.format("File size exceeds maximum allowed size: %d bytes", maxFileSize)
            );
        }
    }

    /**
     * Get file MIME type.
     */
    public String getContentType(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.probeContentType(path);
        } catch (IOException e) {
            log.error("Error getting content type for {}: {}", filePath, e.getMessage());
            return "application/octet-stream";
        }
    }
}
