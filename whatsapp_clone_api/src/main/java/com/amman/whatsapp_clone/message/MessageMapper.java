package com.amman.whatsapp_clone.message;

import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message){
        return new MessageResponse(message.getId()
                ,message.getContent()
                ,message.getType()
                ,message.getSenderId()
                ,message.getReceiverId()
                ,message.getCreatedDate()
                ,null);//to do file thing
    }
}
