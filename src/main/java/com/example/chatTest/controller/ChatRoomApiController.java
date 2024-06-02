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
     * 채팅방 채팅 조회
     * */
    @GetMapping("/api/chat/{chatRoomId}")
    public void messages(){

    }


    /**
     * 유저 채팅방 입장
     * */

//    @GetMapping("/api/chat/chatroom")
//    public Result chatRooms(){
//        List<ChatRoom> chatRooms = chatRoomService.findAll();
//        List<ChatRoomsDto> collection = chatRooms.stream()
//                                        .map(m->ChatRoomsDto.builder()
//                                        .name(m.getName())
//                                        .build())
//                                        .toList();
//        return new Result(collection);
//    }
//
//    @GetMapping("/api/v1/chat/users")
//    public Result chatRoomUsers(){
//        List<ChatRoom> chatRooms = chatRoomService.findAll();
//        List<ChatRoomsDto> collection = chatRooms.stream()
//                .map(m->ChatRoomsDto.builder()
//                        .name(m.getName())
//                        .build())
//                .toList();
//        return new Result(collection);
//    }

//
//    // 유저 입장
//    @PostMapping ("/api/v1/chat/{chatRoomId}")
//    public Result enter(@PathVariable(name="chatRoomId") Long chatRoomId, @RequestParam("userId") Long userId ){
//        chatRoomService.enter(chatRoomId, userId);
//        ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
//        List<ChatRoomListResponseDto> userList = chatRoom.getUserChatRooms().stream()
//                                    .map(m-> ChatRoomListResponseDto.builder()
//                                    .name(m.getUser().getName())
//                                    .build()).toList();
//        // 현재 방의 User name 리스트 반환
//        return new Result(userList);
//    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }
//
}
