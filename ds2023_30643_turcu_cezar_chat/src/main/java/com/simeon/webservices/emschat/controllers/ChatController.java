package com.simeon.webservices.emschat.controllers;

import com.simeon.webservices.emschat.models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/private-chat")
    public ChatMessage receiveMessage(@Payload ChatMessage message){
        System.out.println(message);
        sendNewPrivateMessage(message, message.getReceiverId());
        return message;
    }

    @MessageMapping("/private-chat/{uuid}")
    public void sendNewPrivateMessage(@Payload ChatMessage message, @DestinationVariable("uuid") String uuid){
        template.convertAndSendToUser(uuid, "/private", message);
    }

}
