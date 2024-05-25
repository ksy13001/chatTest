package com.example.chatTest.repository;

import com.example.chatTest.domain.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    UserChatRoom findByUserIdAndChatRoomId(Long userId, Long chatRoomId);
}
