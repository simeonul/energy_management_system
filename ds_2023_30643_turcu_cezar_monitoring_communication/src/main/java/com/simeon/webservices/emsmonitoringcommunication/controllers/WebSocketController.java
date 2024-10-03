package com.simeon.webservices.emsmonitoringcommunication.controllers;

import com.simeon.webservices.emsmonitoringcommunication.entities.ConsumptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/private-message")
    public String receivePrivateMessage(@Payload String message){
        System.out.println("Received message: " + message);
        simpMessagingTemplate.convertAndSendToUser(message, "/private", message + " ASTA AM ADAUGAT DIN BACKEND");
        return message;
    }

    @MessageMapping("/private-message/{uuid}")
    public void sendNewPrivateMessage(@Payload ConsumptionMessage message, @DestinationVariable("uuid") String uuid){
        simpMessagingTemplate.convertAndSendToUser(uuid, "/private", message);
    }

}
