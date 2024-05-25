package com.example.chatTest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<UserChatRoom> userChatRoom = new ArrayList<>();

    @Builder
    public User(String name) {
        this.name = name;
    }

}
