package com.example.chatTest.service;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.repository.ChatRoomRepository;
import com.example.chatTest.repository.UserChatRoomRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.registerCustomDateFormat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(value = false)
@SpringBootTest
class ChatRoomServiceTest {

    @Autowired private ChatRoomRepository chatRoomRepository;
    @Autowired private UserChatRoomRepository userChatRoomRepository;
    @Autowired private ChatRoomService chatRoomService;
    @Autowired private UserService userService;

    @Test
    public void 채팅방_생성() throws Exception{
        //give
        String chatRoomName = "room1";
        String userName = "ksy";
        ChatRoom chatRoom = ChatRoom.builder()
                .name(chatRoomName)
                .totalUser(1)
                .build();
        User user = User.builder()
                .name(userName)
                .build();
        userService.join(user);

        UserChatRoom.setUserChatRoom(user, chatRoom);
        //when
        chatRoomService.create(chatRoom, user.getId());
        //then
        assertThat(chatRoom.getName()).isEqualTo(chatRoomName);
        assertThat(chatRoom.getTotalUser()).isEqualTo(1);
    }

    @Test
    public void 채팅방_입장() throws Exception{
        //given
        // 방생성
        ChatRoom chatRoom = ChatRoom.builder()
                .name("room1")
                .totalUser(1)
                .build();
        // 유저생성
        User start = User.builder().name("start").build();
        User user1 = User.builder().name("user1").build();
        User user2 = User.builder().name("user2").build();
        User user3 = User.builder().name("user3").build();
        // 유저 가입
        Long id0 = userService.join(start);
        Long id1 = userService.join(user1);
        Long id2 = userService.join(user2);
        Long id3 = userService.join(user3);
        //when
        // 채팅방 생성
        chatRoomService.create(chatRoom, id0);
        // 유저 입장
        chatRoomService.enter(chatRoom.getId(), id1);
        chatRoomService.enter(chatRoom.getId(), id2);
        chatRoomService.enter(chatRoom.getId(), id3);
        //then
        assertThat(chatRoom.getTotalUser()).isEqualTo(3);
    }

    @Test
    public void 채팅방_퇴장() throws Exception{
        // 3명에서 2명 나감, 방에 사람 없을떄 방 없어지는거 구현해야함
        //given
        // 방생성
        ChatRoom chatRoom = ChatRoom.builder()
                .name("room1")
                .totalUser(1)
                .build();
        User user1 = User.builder().name("user1").build();
        User user2 = User.builder().name("user2").build();
        User user3 = User.builder().name("user3").build();
        Long id1 = userService.join(user1);
        Long id2 = userService.join(user2);
        Long id3 = userService.join(user3);

        chatRoomService.create(chatRoom, id1);
        chatRoomService.enter(chatRoom.getId(), id2);
        chatRoomService.enter(chatRoom.getId(), id3);
        //when
        chatRoomService.leave(chatRoom.getId(), id1);
        chatRoomService.leave(chatRoom.getId(), id2);
        //then
        assertThat(chatRoom.getTotalUser()).isEqualTo(1);
    }
}