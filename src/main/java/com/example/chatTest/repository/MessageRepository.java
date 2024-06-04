package com.example.chatTest.repository;

import com.example.chatTest.domain.Message;
import com.example.chatTest.service.MessageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatRoomId(Long chatRoomId);

    //List<Message> findAllByChatRoomIdOrderById(Long chatRoomId);
}