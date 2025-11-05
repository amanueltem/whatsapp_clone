package com.amman.whatsapp_clone.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String senderId) {
        return  new ChatResponse(
                senderId,
                chat.getChatName(senderId),
                chat.getUnreadMessages(senderId),
                chat.getLastMessageTime(),
                chat.getRecipient().isUserOnline(),
                chat.getSender().getId(),
                chat.getRecipient().getId()
        );
    }
}
