package com.amman.whatsapp_clone.chat;

import com.amman.whatsapp_clone.user.User;
import com.amman.whatsapp_clone.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository repo;
    private final ChatMapper mapper;
    private final UserRepository userRepo;
    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser){
        final String userId=currentUser.getName();
        return  repo.findChatBySenderId(userId)
                .stream()
                .map(chat -> mapper.toChatResponse(chat,userId))
                .toList();
    }
    public String createChat(String senderId, String receiverId){
        Optional<Chat> existingChat=repo.findChatBySenderIdAndReceiver(senderId,receiverId);
        if(existingChat.isPresent()){
            return  existingChat.get().getId();
        }
        User sender=userRepo.findByPublicId(senderId)
                .orElseThrow(()-> new EntityNotFoundException("User Not found."));
        User receiver=userRepo.findByPublicId(receiverId)
                .orElseThrow(()-> new EntityNotFoundException("User Not found."));
        Chat chat=Chat.builder()
                .sender(sender)
                .recipient(receiver)
                .build();
        return repo.save(chat).getId();
    }
}
