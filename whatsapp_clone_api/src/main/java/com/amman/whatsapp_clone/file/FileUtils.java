package com.amman.whatsapp_clone.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileUtils {
    private FileUtils(){
    }
    public  static byte[] readFileFromLocation(String fileUrl){
        if (fileUrl == null || fileUrl.isBlank()) {
            return new byte[0];
        }
        try{
            Path file= Paths.get(fileUrl);
            return Files.readAllBytes(file);
        }
        catch (IOException e){
            log.error("not readable {}",e.getMessage());
        }
        return new byte[0];
    }
}
