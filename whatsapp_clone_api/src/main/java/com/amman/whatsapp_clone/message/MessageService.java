package com.amman.whatsapp_clone.message;

import com.amman.whatsapp_clone.chat.Chat;
import com.amman.whatsapp_clone.chat.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repo;
    private final ChatRepository chatRepo;
    public  void saveMessage(MessageRequest messageRequest){
        Chat chat=chatRepo.findChatBySenderIdAndReceiver(messageRequest.senderId(),messageRequest.receiverId())
                .orElseThrow(
                        ()-> new EntityNotFoundException("Chat Not found.")
                );
        Message message= Message.builder()
                .content(messageRequest.content())
                .chat(chat)
                .receiverId(messageRequest.receiverId())
                .senderId(messageRequest.senderId())
                .type(messageRequest.type())
                .state(MessageState.SENT)
                .build();
        repo.save(message);
        //to do notification
    }
}
