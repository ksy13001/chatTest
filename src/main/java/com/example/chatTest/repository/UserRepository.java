package com.example.chatTest.repository;

import com.example.chatTest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    @Query("SELECT u FROM User u JOIN FETCH u.userChatRoom WHERE u.id = :userId")
    User findByIdWithUserChatRoom(@Param("userId") Long userId);
}
