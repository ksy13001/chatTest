package com.example.chatTest.service;

import com.example.chatTest.domain.User;
import com.example.chatTest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    public void 유저생성() throws Exception{
        //given
        String userName = "ksy";
        User user = User.builder()
                .name(userName)
                .build();
        //when
        Long userId = userService.join(user);
        User findUser = userService.findById(userId);
        //then
        assertThat(userName).isEqualTo(findUser.getName());
    }

}