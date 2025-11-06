package com.amman.whatsapp_clone.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    @Value("${application.file.upload.media-output-path}")
    private String fileUploadPath;
    public String saveFile(@NonNull  MultipartFile sourceFile,
                          @NonNull String senderId) {
         final String fileUploadSubPath="users"+ File.separator+senderId;
        return uploadFile(sourceFile,fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile file, @NonNull String subPath) {
        Path uploadDir = Paths.get(fileUploadPath, subPath);

        try {
            Files.createDirectories(uploadDir); // creates if not exists
            String ext = getFileExtension(file.getOriginalFilename());
            Path target = uploadDir.resolve(System.currentTimeMillis() + ext);
            Files.write(target, file.getBytes());
            log.info("File saved to {}", target);
            return target.toString();
        } catch (IOException e) {
            log.error("Failed to save file", e);
            return null;
        }
    }

    private String getFileExtension(String name) {
        return Optional.ofNullable(name)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".")).toLowerCase())
                .orElse("");
    }

}
