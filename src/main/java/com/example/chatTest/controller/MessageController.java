package com.example.chatTest.controller;

import com.example.chatTest.domain.*;
import com.example.chatTest.dto.MessageDto;
import com.example.chatTest.dto.MessageListDto;
import com.example.chatTest.service.ChatRoomService;
import com.example.chatTest.service.MessageService;
import com.example.chatTest.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

/**
 *  메세지 관련 컨르롤러
 * */

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final SimpMessageSendingOperations template;

    /**
     * 메세지 전송
     * 메세지타입 : CHAT, JOIN, LEAVE, PLAYER
     * */
    @MessageMapping("/api/chat/{chatRoomId}")
    public void send(@Payload MessageDto messageDto)
    {
        if(messageDto.getType() == MessageType.JOIN){

        }
        if(messageDto.getType() == MessageType.CHAT) {
            messageService.save(messageDto);
            template.convertAndSend("/sub/api/chat/" + messageDto.getChatRoomId(), messageDto);
        }
    }

    /**
     * 채팅방의 모든 채팅 조회
     * */
    @GetMapping("/api/chat/{chatRoomId}")
    public Result messages(@PathVariable(name = "chatRoomId")Long chatRoomId ){
        return new Result(messageService.findAllDesc(chatRoomId));
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

}
