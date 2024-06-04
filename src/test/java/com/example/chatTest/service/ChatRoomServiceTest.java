package com.example.chatTest.service;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.ChatRoomType;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.CreateChatRoomRequestDto;
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

        // 유저
        User user = User.builder()
                .name(userName)
                .build();
        userService.join(user);

        CreateChatRoomRequestDto request = CreateChatRoomRequestDto.builder()
                .name(chatRoomName)
                .userId(user.getId())
                .typeId(ChatRoomType.SONG, 1L)
                .build();
        //when
        Long chatRoomId = chatRoomService.create(request);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        //then
        assertThat(chatRoom.getName()).isEqualTo(chatRoomName);
        assertThat(chatRoom.getTotalMember()).isEqualTo(1);
    }

    @Test
    public void 채팅방_입장() throws Exception{
        // 3명이 1번방에 입장. 1번방은 start가 생성
        //given

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

        // 채팅방 dto 생성
        String chatRoomName = "room1";

        CreateChatRoomRequestDto request = CreateChatRoomRequestDto.builder()
                .name(chatRoomName)
                .userId(id0)
                .typeId(ChatRoomType.SONG, 1L)
                .build();

        //when
        // 채팅방 생성
        Long chatRoomId = chatRoomService.create(request);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        // 유저 입장
        chatRoomService.enterChatRoom(id1, chatRoomId);
        chatRoomService.enterChatRoom(id2, chatRoomId);
        chatRoomService.enterChatRoom(id3, chatRoomId);
        //then
        assertThat(chatRoom.getCurrentMember()).isEqualTo(4);
        // 제일 첫번째 사람
        assertThat(chatRoom.getMasterId()).isEqualTo(id0);
    }
//
    @Test
    public void 채팅방_퇴장() throws Exception{
        // 4명에서 2명 나감, 방에 사람 없을떄 방 없어지는거 구현해야함
        //given
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

        // 채팅방 dto 생성
        String chatRoomName = "room1";

        CreateChatRoomRequestDto request = CreateChatRoomRequestDto.builder()
                .name(chatRoomName)
                .userId(id0)
                .typeId(ChatRoomType.SONG, 1L)
                .build();

        // 채팅방 생성
        Long chatRoomId = chatRoomService.create(request);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);

        // 유저 입장
        chatRoomService.enterChatRoom(id1, chatRoomId);
        chatRoomService.enterChatRoom(id2, chatRoomId);
        chatRoomService.enterChatRoom(id3, chatRoomId);

        //when
        //유저 퇴장
        chatRoomService.leaveChatRoom(id0, chatRoomId);
        chatRoomService.leaveChatRoom(id1, chatRoomId);
        //then
        // 4-2
        assertThat(chatRoom.getCurrentMember()).isEqualTo(2);
        // 현재 방에서 가장 오래된 사람은 id2
        assertThat(chatRoom.getMasterId()).isEqualTo(id2);
  }
}