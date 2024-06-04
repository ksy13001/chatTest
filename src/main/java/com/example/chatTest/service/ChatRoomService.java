package com.example.chatTest.service;

import com.example.chatTest.domain.User;
import com.example.chatTest.domain.UserChatRoom;
import com.example.chatTest.dto.ChatRoomDetailDto;
import com.example.chatTest.dto.ChatRoomListResponseDto;
import com.example.chatTest.dto.CreateChatRoomRequestDto;
import com.example.chatTest.dto.JoinChatRoomRequestDto;
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
    public Long create(CreateChatRoomRequestDto request){
        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());

        // 채팅방 생성유저
        Long userId = request.getUserId();
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
     * 채팅방 조회(테스트용)
     * */
    @Transactional(readOnly = true)
    public ChatRoom findById(Long chatRoomId){
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new IllegalArgumentException("채팅방이 없음 id:"+chatRoomId));
    }

    /**
     * 채팅방 상세 조회
     * */
    @Transactional(readOnly = true)
    public ChatRoomDetailDto findChatRoomDetail(Long chatRoomId){
        ChatRoom findChatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new IllegalArgumentException("채팅방이 없음 id:"+chatRoomId));

        return new ChatRoomDetailDto(findChatRoom);
    }

    /**
     *  유저 채팅방 삭제
     * */
    public void deleteChatRoom(Long chatRoomId){
        chatRoomRepository.deleteById(chatRoomId);
    }


    /**
     * 유저 채팅방 입장
     * */
    public void enterChatRoom(Long userId, Long chatRoomId){
        // 유저, 채팅방 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저가 없습니다. id"+userId));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new IllegalArgumentException("채팅방이 없습니다. id:"+chatRoomId));

        // masterId 얻고 이 유저의 현재 재생 시각 get
        Long masterId = chatRoom.getMasterId();
        // 현재 재생시각 적용

        // UserChatRoom 에 유저 등록
        UserChatRoom.setUserChatRoom(user, chatRoom);
        // currentMember += 1
        chatRoom.addUser();
    }

    /**
     * 유저 채팅방 퇴장
     * */
    public void leaveChatRoom(Long userId, Long chatRoomId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("유저가 없습니다. id"+userId));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(()->new IllegalArgumentException("채팅방이 없습니다. id:"+chatRoomId));


        UserChatRoom userChatRoom = userChatRoomRepository.findByUserIdAndChatRoomId(userId, chatRoomId);
        // 중간 테이블 삭제
        userChatRoomRepository.delete(userChatRoom);
        // User, ChatRoom 에도 삭제 적용
        UserChatRoom.deleteUserChatRoom(userChatRoom, user, chatRoom);
        // current 1 감소
        chatRoom.subUser();
    }

}