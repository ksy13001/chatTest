package com.example.chatTest.service;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.Message;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.ChatRoomListResponseDto;
import com.example.chatTest.dto.MessageDto;
import com.example.chatTest.dto.MessageListDto;
import com.example.chatTest.repository.ChatRoomRepository;
import com.example.chatTest.repository.MessageRepository;
import com.example.chatTest.repository.UserChatRoomRepository;
import com.example.chatTest.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    public Long save(MessageDto messageDto){

        UserChatRoom userChatRoom = userChatRoomRepository
                .findByUserIdAndChatRoomId(messageDto.getUserId(), messageDto.getChatRoomId());
        return messageRepository.save(messageDto.toEntity(userChatRoom)).getId();
    }

    /**
     * 채팅방 채팅 조회
     * */
    @Transactional(readOnly = true)
    public List<MessageListDto> findAllDesc(Long chatRoomId){
        return messageRepository.findAllByChatRoomId(chatRoomId).stream()
                .map(MessageListDto::new)
                .collect(Collectors.toList());
    }



}
