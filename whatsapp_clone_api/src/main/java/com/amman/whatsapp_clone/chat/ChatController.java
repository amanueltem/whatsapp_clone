package com.amman.whatsapp_clone.chat;

import com.amman.whatsapp_clone.common.StringResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService service;

    @PostMapping
    public ResponseEntity<StringResponse> createChat(@RequestParam String senderId,
                                                     @RequestParam String receiverId) {
        String chatId = service.createChat(senderId, receiverId);
        return ResponseEntity.ok(new StringResponse(chatId));
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChat(Authentication currentUser) {
        return ResponseEntity.ok(service.getChatsByReceiverId(currentUser));
    }
}
