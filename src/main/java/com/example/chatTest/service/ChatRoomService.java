package com.example.chatTest.service;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.repository.ChatRoomRepository;
import com.example.chatTest.repository.UserChatRoomRepository;
import com.example.chatTest.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    // 채팅방 생성
    public Long create(ChatRoom chatRoom, Long memberId){
        // 채팅방 생성하려는 유저 찾기
        User user = userRepository.findById(memberId)
                        .orElseThrow(()->
                        new IllegalArgumentException("채팅방이 존재하지 않습니다. id="
                                + chatRoom.getId()));

        // UserChatRoom 생성 - UserChatRoom 에서 연관관계 편의 메서드 작성
        chatRoomRepository.save(chatRoom);
        UserChatRoom.setUserChatRoom(user, chatRoom);
        return chatRoom.getId();
    }

    @Transactional(readOnly = true)
    public ChatRoom findById(Long chatRoomId){
         return chatRoomRepository.findById(chatRoomId)
                 .orElseThrow(()->
                         new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
    }

    // 입장 -> totalUser += 1, UserChatRoom 업데이트
    public Long enter(Long chatRoomId, Long userId){
        // 방찾기
        ChatRoom chatRoom = chatRoomRepository
                            .findById(chatRoomId)
                            .orElseThrow(()->
                                new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
        // 입장유저 찾기
        User user = userRepository.findById(userId)
                        .orElseThrow(()->
                        new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));

        chatRoom.addUser();
        UserChatRoom.setUserChatRoom(user, chatRoom);
        return chatRoom.getId();
    }

    public Long leave(Long chatRoomId, Long userId){
        // 방찾기
        ChatRoom chatRoom = chatRoomRepository
                .findById(chatRoomId)
                .orElseThrow(()->
                        new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
        // 입장유저 찾기
        User user = userRepository
                .findById(userId)
                .orElseThrow(()->
                        new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
        chatRoom.subUser();
//        UserChatRoom userChatRoom = userChatRoomRepository.findByUserIdAndChatRoomId(userId, chatRoomId);
//        userChatRoomRepository.delete(userChatRoom);

        return chatRoom.getId();
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findAll(){
        return chatRoomRepository.findAll();
    }

    @PostConstruct
    public void chatOn(){

    }
}
