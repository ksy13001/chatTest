package com.example.chatTest.domain;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 *  채팅방 엔티티
 *
 * */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;

    /* currentSong */
    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private final List<UserChatRoom> userChatRooms = new ArrayList<>();

    private String imageUrl;

    // 현재 방의 총 인원 수
    private int totalUser;

    @Builder
    public ChatRoom(String name, int totalUser) {
        this.name = name;
        this.totalUser = totalUser;
    }

    /**
     * 비즈니스 메서드
     **/
    // 방에 사람 들어오고
    public void addUser() {
        this.totalUser += 1;
    }

    // 나가고
    public void subUser() {
        if (totalUser > 0) {
            this.totalUser -= 1;
        }
    }
}


