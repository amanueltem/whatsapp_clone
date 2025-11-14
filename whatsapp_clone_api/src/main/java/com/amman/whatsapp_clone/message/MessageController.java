package com.amman.whatsapp_clone.message;

import com.amman.whatsapp_clone.chat.ChatResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Tag(name = "Message")
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
            ,
            @Parameter()
            @RequestParam("file")MultipartFile file
            , Authentication connectedUser){
        service.uploadMediaMessage(chatId,file,connectedUser);
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  void setMessagesToSeen(@RequestParam String chatId, Authentication connectedUser){
        service.setMessagesToSeen(chatId,connectedUser);
    }
    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable("chat-id") String chatId){
        return  ResponseEntity.ok(service.findChatMessages(chatId));
    }
}
