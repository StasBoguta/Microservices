package com.example.Microservices.repository;

import com.example.Microservices.model.ChatMessage;
import com.example.Microservices.model.ChatRoom;
import com.mentor4you.model.ChatMessage;
import com.mentor4you.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String s, String id);

    List<ChatRoom> findAllBySenderId(String toString);

    List<ChatRoom> findAllByRecipientId(String toString);

    List<ChatRoom> findAllBySenderIdAndRecipientId(String tostring, String tostring2);

    void insert(ChatMessage message);
}
