package com.amman.whatsapp_clone.message;

import com.amman.whatsapp_clone.chat.Chat;
import com.amman.whatsapp_clone.chat.ChatRepository;
import com.amman.whatsapp_clone.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repo;
    private final ChatRepository chatRepo;
    private final MessageMapper mapper;
    private final FileService fileService;
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
    public List<MessageResponse> findChatMessages(String chatId){
        return repo.findMessageByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }
    public  void uploadMediaMessage(String chatId, MultipartFile file, Authentication currentUser){
        Chat chat=chatRepo.findById(chatId)
                .orElseThrow(()-> new EntityNotFoundException("chat not found."));
        final String senderId=getSenderId(chat,currentUser);
        final String receiverId=getRecipientId(chat,currentUser);
        final String filePath=fileService.saveFile(file,senderId);
        Message message= Message
                .builder()
                .chat(chat)
                .senderId(senderId)
                .receiverId(receiverId)
                .type(MessageType.IMAGE)
                .state(MessageState.SEEN)
                .mediaFilePath(filePath)
                .build();
        repo.save(message);
        //to do notification

    }

    private String getSenderId(Chat chat, Authentication currentUser) {
        if(chat.getSender().getId().equals(currentUser.getName())){
            return  chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    @Transactional
    public  void setMessagesToSeen(String chatId, Authentication currentUser){
        Chat chat=chatRepo.findById(chatId)
                .orElseThrow(()-> new EntityNotFoundException("chat not found."));
        //final String recipientId=getRecipientId(chat,currentUser);
        //to do notification
        repo.SetMessagesToSeenByChatId(chat.getId(),MessageState.SEEN);
    }

    private String getRecipientId(Chat chat, Authentication currentUser) {
        if(chat.getSender().getId().equals(currentUser.getName())){
            return chat.getRecipient().getId();
        }
        return  chat.getSender().getId();
    }
}
