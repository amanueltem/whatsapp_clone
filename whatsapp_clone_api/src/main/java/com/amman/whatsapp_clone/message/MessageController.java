package com.amman.whatsapp_clone.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequest request){
        service.saveMessage(request);
    }
    @PostMapping(value = "/upload-media",consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public  void uploadMedia(
            @RequestParam String chatId
            , @RequestParam("file")MultipartFile file
            , Authentication connectedUser){
        service.uploadMediaMessage(chatId,file,connectedUser);
    }
}
