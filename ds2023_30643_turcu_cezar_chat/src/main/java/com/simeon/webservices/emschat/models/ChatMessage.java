package com.simeon.webservices.emschat.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
    private String senderId;
    private String receiverId;
    private String content;
    private MessageType type;
}
