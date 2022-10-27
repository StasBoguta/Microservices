package com.example.Microservices.service;


import com.example.Microservices.exeptions.ChatNotFoundException;
import com.example.Microservices.model.ChatMessage;
import com.example.Microservices.model.ChatRoom;
import com.example.Microservices.model.User;
import com.example.Microservices.repository.ChatMessageRepository;
import com.example.Microservices.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {



    @Autowired

    ChatRoomRepository chatRoomRepository;
    ChatMessageRepository chatMessageRepository;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository) {
               this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public User findUserByRequest(HttpServletRequest request){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/api/users/userByRequest/{request}" , User.class);

    }

    public User findUserById(Integer id){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("  http://localhost:8080/api/users/userById{id}" , User.class);

    }


    public Optional<ChatRoom> getRoom(HttpServletRequest request, String id) {


        User user = userRepository.findUserByEmail(email);

        String userId = Integer.toString(user.getId());

        Optional<ChatRoom> roomOptional = chatRoomRepository.findBySenderIdAndRecipientId(userId, id);
        if (roomOptional.isEmpty()) {
            throw new ChatNotFoundException("Chat not found");
        }
        return roomOptional;
    }

    public ChatRoom createRoom(HttpServletRequest request, String id) {


        User user = userRepository.findUserByEmail(email);
        String userId = Integer.toString(user.getId());

        User recipient = userRepository.findOneById(Integer.parseInt(id));

        String chatId = String.format("%s_%s", userId, id);
        ChatRoom room = new ChatRoom(chatId, userId, id);
        chatRoomRepository.save(room);
        String chatId1 = String.format("%s_%s", id, userId);
        ChatRoom room1 = new ChatRoom(chatId1, id, userId);
        chatRoomRepository.save(room1);
        return room;
    }

    public List<ChatRoom> findMyRoom(HttpServletRequest request) {

        User user = userRepository.findUserByEmail(email);

        List<ChatRoom> roomList = chatRoomRepository.findAllBySenderId(Integer.toString(user.getId()));
        return roomList;
    }

    public ResponseEntity<List<ChatMessage>> findMessage(String sendid, String recivid) {
        List<ChatRoom> messages = chatRoomRepository.findAllBySenderIdAndRecipientId(sendid,recivid);
        if (messages.size() > 0) {
            messages.get(0).getChatId();
            List<ChatMessage> chats = chatMessageRepository.findAllByChatId( messages.get(0).getChatId());

            return new ResponseEntity<>(chats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
