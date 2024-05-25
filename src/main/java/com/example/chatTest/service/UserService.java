package com.example.chatTest.service;

import com.example.chatTest.domain.User;
import com.example.chatTest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long join(User user){
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("유저가 존재하지 않습니다. id=" + id));
    }
}
