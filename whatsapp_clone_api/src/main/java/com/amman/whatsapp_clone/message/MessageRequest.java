package com.amman.whatsapp_clone.message;

public record MessageRequest(
        String content,
        String senderId,
        String receiverId,
        MessageType type,
        String chatId
){
}
