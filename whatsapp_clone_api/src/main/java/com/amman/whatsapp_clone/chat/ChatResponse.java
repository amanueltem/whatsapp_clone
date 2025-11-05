package com.amman.whatsapp_clone.chat;
import java.time.LocalDateTime;

public record ChatResponse(
        String id,
        String name,
        long unreadCount,
        LocalDateTime lastMessageTime,
        boolean isPresentOnline,
        String senderId,
        String receiverId
) {
}
