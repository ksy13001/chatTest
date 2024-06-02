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
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "chatRoom_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private final List<UserChatRoom> userChatRooms = new ArrayList<>();

    private String imageUrl;

    // 현재 방에 있는 사람 수
    private int currentMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="song_id")
    private Song song;

    @Builder
    public ChatRoom(String name) {
        this.name = name;
        this.currentMember = 1; // 생성될때 멤버 1명
    }
    /**
     * 비즈니스 메서드
     **/
    public int getTotalMember(){
        return this.getUserChatRooms().size();
    }

//    // 방에 사람 들어오고
//    public void addUser() {
//        this.totalUser += 1;
//    }
//
//    // 나가고
//    public void subUser() {
//        if (totalUser > 0) {
//            this.totalUser -= 1;
//        }
//    }
}


