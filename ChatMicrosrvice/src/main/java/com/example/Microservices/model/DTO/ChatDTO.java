package com.example.Microservices.model.DTO;

import com.example.Microservices.model.ChatRoom;


public class ChatDTO extends ChatRoom {

    private String name;
    private String avatar;

    public ChatDTO(String id, String chatId, String senderId, String recipientId, String name, String avatar) {

        super(id, chatId, senderId, recipientId);
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
