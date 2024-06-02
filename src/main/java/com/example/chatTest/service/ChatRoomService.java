package com.example.chatTest.service;

import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.ChatRoomListResponseDto;
import com.example.chatTest.dto.CreateChatRoomRequestDto;
import com.example.chatTest.repository.ChatRoomRepository;
import com.example.chatTest.repository.UserChatRoomRepository;
import com.example.chatTest.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.chatTest.domain.ChatRoom;

import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserChatRoomRepository userChatRoomRepository;

    /**
     * 채팅방 생성
     * */
    public Long create(CreateChatRoomRequestDto createChatRoomRequestDto){
        ChatRoom chatRoom = chatRoomRepository.save(createChatRoomRequestDto.toEntity());

        // 채팅방 생성유저
        Long userId = createChatRoomRequestDto.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
        UserChatRoom.setUserChatRoom(user, chatRoom);


        return chatRoom.getId();
    }

    /**
     * 채팅방 목록 조회
     * */
    @Transactional(readOnly = true)
    public List<ChatRoomListResponseDto> findAllDesc(){
        return chatRoomRepository.findAllDesc().stream()
                .map(ChatRoomListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 입장
     * */
    public void joinChatRoom(){

    }


//    @Transactional(readOnly = true)
//    public ChatRoom findById(Long chatRoomId){
//         return chatRoomRepository.findById(chatRoomId)
//                 .orElseThrow(()->
//                         new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
//    }

//
//    // 입장 -> totalUser += 1, UserChatRoom 업데이트
//    public Long enter(Long chatRoomId, Long userId){
//        // 방찾기
//        ChatRoom chatRoom = chatRoomRepository
//                            .findById(chatRoomId)
//                            .orElseThrow(()->
//                                new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
//        // 입장유저 찾기
//        User user = userRepository.findById(userId)
//                        .orElseThrow(()->
//                        new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
//
//        chatRoom.addUser();
//        UserChatRoom.setUserChatRoom(user, chatRoom);
//        return chatRoom.getId();
//    }
//
//    public Long leave(Long chatRoomId, Long userId){
//        // 방찾기
//        ChatRoom chatRoom = chatRoomRepository
//                .findById(chatRoomId)
//                .orElseThrow(()->
//                        new IllegalArgumentException("채팅방이 존재하지 않습니다. id=" + chatRoomId));
//        // 입장유저 찾기
//        User user = userRepository
//                .findById(userId)
//                .orElseThrow(()->
//                        new IllegalArgumentException("유저가 존재하지 않습니다. id=" + userId));
//        chatRoom.subUser();
////        UserChatRoom userChatRoom = userChatRoomRepository.findByUserIdAndChatRoomId(userId, chatRoomId);
////        userChatRoomRepository.delete(userChatRoom);
//
//        return chatRoom.getId();
//    }
//
//    @Transactional(readOnly = true)
//    public List<ChatRoom> findAll(){
//        return chatRoomRepository.findAll();
//    }
//
//    @PostConstruct
//    public void chatOn(){
//
//    }
}
