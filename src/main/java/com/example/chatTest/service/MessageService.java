package com.example.chatTest.service;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.Message;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.MessageDto;
import com.example.chatTest.repository.ChatRoomRepository;
import com.example.chatTest.repository.MessageRepository;
import com.example.chatTest.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    public Long save(Message message){
        return messageRepository.save(message).getId();
    }

    public Message sendMessage(Long userId, Long chatRoomId, String content){
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("유저가 존재하지 않습니다. id="
                + userId));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(()->new IllegalArgumentException("채팅방이 존재하지 않습니다. id="
                + chatRoomId));
        UserChatRoom userChatRoom = UserChatRoom.setUserChatRoom(user, chatRoom);
        Message message = Message.builder()
                .content(content)
                .userChatRoom(userChatRoom)
                .build();

        return messageRepository.save(message);
    }

    public Message setMessage(MessageDto messageDto, User user, ChatRoom chatRoom){
        UserChatRoom userChatRoom = UserChatRoom.setUserChatRoom(user, chatRoom);
        return Message.builder()
                .content(messageDto.getContent())
                .userChatRoom(userChatRoom)
                .build();
    }

}
