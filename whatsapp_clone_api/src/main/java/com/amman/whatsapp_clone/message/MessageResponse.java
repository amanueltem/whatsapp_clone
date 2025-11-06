package com.amman.whatsapp_clone.message;

import java.time.LocalDateTime;

public record MessageResponse(
        Long id,
        String content,
        MessageType type,
        String senderId,
        String receiverId,
        LocalDateTime createdDate,
        byte[] media
) {
}
