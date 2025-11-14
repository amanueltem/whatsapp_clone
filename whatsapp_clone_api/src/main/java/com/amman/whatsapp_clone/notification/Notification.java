package com.amman.whatsapp_clone.notification;

import com.amman.whatsapp_clone.message.MessageType;

public record Notification(
        String chatId,
        String content,
        String senderId,
        String receiverId,
        MessageType messageType,
        NotificationType type,
        String chatName,
        byte[] media
) {
}
