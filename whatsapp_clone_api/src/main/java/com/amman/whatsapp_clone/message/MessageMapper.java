package com.amman.whatsapp_clone.message;

import com.amman.whatsapp_clone.file.FileUtils;
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
                , FileUtils.readFileFromLocation(message.getMediaFilePath())
        );
    }
}
