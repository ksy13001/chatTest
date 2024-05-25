package com.example.chatTest.controller;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.Message;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.*;
import com.example.chatTest.service.ChatRoomService;
import com.example.chatTest.service.MessageService;
import com.example.chatTest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final MessageService messageService;



    @PostMapping("/api/v1/chat")
    public CreateChatRoomResponseDto createChatRoom(
            @RequestBody CreateChatRoomRequestDto request, @RequestParam("userId") Long userId)
    {
        // ChatRoom 객체 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .name(request.getName())
                .totalUser(1)   // 처음 들어왔을때 1명
                .build();
        // ChatRoom 생성
        Long createdChatRoomId = chatRoomService.create(chatRoom, userId);
        ChatRoom createdChatRoom = chatRoomService.findById(createdChatRoomId);
        return CreateChatRoomResponseDto.builder()
                .id(createdChatRoom.getId())
                .name(createdChatRoom.getName())
                .build();
    }

    @GetMapping("/api/v1/chat")
    public Result chatRooms(){
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        List<ChatRoomsDto> collection = chatRooms.stream()
                                        .map(m->ChatRoomsDto.builder()
                                        .name(m.getName())
                                        .build())
                                        .toList();
        return new Result(collection);
    }

    @GetMapping("/api/v1/chat/users")
    public Result chatRoomUsers(){
        List<ChatRoom> chatRooms = chatRoomService.findAll();
        List<ChatRoomsDto> collection = chatRooms.stream()
                .map(m->ChatRoomsDto.builder()
                        .name(m.getName())
                        .build())
                .toList();
        return new Result(collection);
    }


    // 유저 입장
    @PostMapping ("/api/v1/chat/{chatRoomId}")
    public Result enter(@PathVariable(name="chatRoomId") Long chatRoomId, @RequestParam("userId") Long userId ){
        chatRoomService.enter(chatRoomId, userId);
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
        List<ChatRoomUserResponseDto> userList = chatRoom.getUserChatRooms().stream()
                                    .map(m-> ChatRoomUserResponseDto.builder()
                                    .name(m.getUser().getName())
                                    .build()).toList();
        // 현재 방의 User name 리스트 반환
        return new Result(userList);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }


}
