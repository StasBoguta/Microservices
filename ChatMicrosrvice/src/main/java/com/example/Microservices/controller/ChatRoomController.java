package com.example.Microservices.controller;


import com.example.Microservices.exeptions.ChatNotFoundException;
import com.example.Microservices.model.ChatMessage;
import com.example.Microservices.model.ChatRoom;
import com.example.Microservices.model.DTO.ChatDTO;
import com.example.Microservices.repository.ChatMessageRepository;
import com.example.Microservices.repository.ChatRoomRepository;
import com.example.Microservices.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ChatRoomController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatRoomRepository chatRoomRepository;
    ChatService chatService;
    UserService userService;

    public ChatRoomController(SimpMessagingTemplate simpMessagingTemplate, ChatRoomRepository chatRoomRepository, ChatService chatService, UserService userService, ChatMessageRepository chatMessageRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRoomRepository = chatRoomRepository;
        this.chatService = chatService;
        this.userService = userService;
        this.chatMessageRepository = chatMessageRepository;
    }

    ChatMessageRepository chatMessageRepository;



    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        chatMessageRepository.insert(message);
    }

    @GetMapping("/all")
    ResponseEntity<?> getAll(){
        return ResponseEntity.ok(chatRoomRepository.findAll());
    }

    //get all chat
    @GetMapping("/chat")
    ResponseEntity<?> getAllMyRoom(HttpServletRequest request){
        List<ChatRoom> chatList = chatService.findMyRoom(request);
        List<ChatDTO> list = new ArrayList<>();
        for(ChatRoom  s : chatList){
            User user = userService.getUserById(s.getRecipientId());
            list.add(new ChatDTO(s.getId(),s.getChatId(),s.getSenderId(),s.getRecipientId(),user.getFirst_name(),user.getAvatar()));
        }
        return ResponseEntity.ok(list);
    }

    //get or add new Chat room
    @PostMapping("/chat/user/{id}")
    ResponseEntity<?> getRoom(HttpServletRequest request, @PathVariable String id) {
        try {
            Optional<ChatRoom> chatRoom = chatService.getRoom(request, id);
            User user = userService.getUserById(id);
            ChatDTO result = new ChatDTO(chatRoom.get().getId(),chatRoom.get().getChatId(),chatRoom.get().getSenderId(),chatRoom.get().getRecipientId(),user.getFirst_name(),user.getAvatar());
            return ResponseEntity.ok(result);
        }catch (NullPointerException | ChatNotFoundException e){
            ChatRoom chatRoom = chatService.createRoom(request, id);
            User user = userService.getUserById(id);
            ChatDTO result = new ChatDTO(chatRoom.getId(),chatRoom.getChatId(),chatRoom.getSenderId(),chatRoom.getRecipientId(),user.getFirst_name(),user.getAvatar());
            return ResponseEntity.ok(result);
        }

        }
    @Operation(summary = "")
    @GetMapping("/findmessage/{sendid}/{recivid}")
    ResponseEntity<List<ChatMessage>> findmessage(@PathVariable("sendid") String sendid,
                                                 @PathVariable("recivid") String recivid) {
        List<ChatRoom> messages = chatRoomRepository.findAllBySenderIdAndRecipientId(sendid,recivid);
        if (messages.size() > 0) {
            return chatService.findMessage(sendid,recivid);
        } else {
            return new ResponseEntity<List<ChatMessage>>(HttpStatus.NOT_FOUND);
        }
    }




}
