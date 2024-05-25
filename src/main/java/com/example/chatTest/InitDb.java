package com.example.chatTest;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.User;
import com.example.chatTest.service.ChatRoomService;
import com.example.chatTest.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class InitDb {
    private final InitService initService;


    @PostConstruct // 의존성 주입 후 실행시켜줌
    public void init(){
        initService.DbInit1();
    }

    @Transactional
    @RequiredArgsConstructor
    @Component
    static class InitService{   // db 샘플 데이터 생성
        // static으로 선언하면 순환 x
        private final EntityManager em;
        private final UserService userService;
        private final ChatRoomService chatRoomService;

        public void DbInit1(){
            // 맴버 3명, 채팅룸 1개
            User user1 = User.builder().name("user1").build();
            User user2 = User.builder().name("user2").build();
            User user3 = User.builder().name("user3").build();
            Long id1 = userService.join(user1);
            Long id2 = userService.join(user2);
            Long id3 = userService.join(user3);
            ChatRoom chatRoom = ChatRoom.builder().name("chatroom1").totalUser(1).build();
            Long chatRoomId = chatRoomService.create(chatRoom, id1);
            chatRoomService.enter(chatRoomId, id2);
            chatRoomService.enter(chatRoomId, id3);


        }

    }

}
