package com.example.chatTest.repository;

import com.example.chatTest.domain.ChatRoom;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(value = false)
@SpringBootTest
class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Test
    public void save() throws Exception{
        String name = "1st";
        //given
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .build();
        //when
        chatRoomRepository.save(chatRoom);
        //then
        ChatRoom findChatRoom = chatRoomRepository.findAll().get(0);
        assertThat(findChatRoom.getName()).isEqualTo(name);
    }
}