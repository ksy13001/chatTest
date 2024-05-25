package com.example.chatTest.controller;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.Message;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.MessageDto;
import com.example.chatTest.service.ChatRoomService;
import com.example.chatTest.service.MessageService;
import com.example.chatTest.service.UserService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final MessageSendingOperations messageSendingOperations;

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/api/v1/chat/{chatRoomId}")
    @SendTo("/sub/api/v1/chat/{chatRoomId}")
    public MessageDto send(@DestinationVariable Long chatRoomId,
                           @Payload MessageDto messageDto)
    {      // 트렌젝션 범위내에서 실행, 서비스랑 컨트롤러 분리좀 하기
//        User user = userService.findById(messageDto.getUserId());
//        ChatRoom chatRoom = chatRoomService.findById(messageDto.getRoomId());
//        logger.info("messageDto: " + messageDto.getUserId() + " " + messageDto.getRoomId());
//        logger.info("chatRoom: " + chatRoom.getId() + " " + chatRoom.getName() + " " + chatRoom.getTotalUser());
//        logger.info("user: " + user.getId() +" "+ user.getName());
//        Message message = messageService.setMessage(messageDto, user, chatRoom);
//        UserChatRoom userChatRoom = message.getUserChatRoom();
//        messageSendingOperations.convertAndSend(MessageDto.builder()
//                .id(message.getId())
//                .content(message.getContent())
//                .roomId(userChatRoom.getChatRoom().getId())
//                .userId(userChatRoom.getUser().getId())
//                .build());
        logger.info("메세지 저장");
        Message message = messageService.sendMessage(messageDto.getUserId(), messageDto.getRoomId(), messageDto.getContent());


        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .roomId(message.getUserChatRoom().getChatRoom().getId())
                .userId(message.getUserChatRoom().getUser().getId())
                .build();
    }
}
