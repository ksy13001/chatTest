package com.example.chatTest.controller;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.ChatRoomStatus;
import com.example.chatTest.domain.ChatRoomType;
import com.example.chatTest.dto.*;
import com.example.chatTest.service.ChatRoomService;
import com.example.chatTest.service.MessageService;
import com.example.chatTest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomApiController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final MessageService messageService;

    /**
     *  채팅방 생성
     * */
    @PostMapping("/api/chat/chatroom")
    public CreateChatRoomResponseDto createChatRoom(@RequestBody CreateChatRoomRequestDto request) {
        Long chatRoomId = chatRoomService.create(request);

        return CreateChatRoomResponseDto.builder().chatRoomId(chatRoomId).build();
    }

    /**
     *  채팅방 목록 조회
     * */
    @GetMapping("/api/chat/chatroom")
    public Result chatRooms(){
        return new Result(chatRoomService.findAllDesc());
    }


    /**
     * 채팅방 상세 조회
     * */
    @GetMapping("/api/chat/chatroom/{chatRoomId}")
    public ChatRoomDetailDto findChatRoomDetail(@PathVariable(name = "chatRoomId")Long chatRoomId){
        return chatRoomService.findChatRoomDetail(chatRoomId);
    }

    /**
     * 채팅방 찾기
     * */


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
}
