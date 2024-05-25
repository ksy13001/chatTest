package com.example.chatTest.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Message {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userChatRoom_id")
    UserChatRoom userChatRoom;

    private String content;

    @Builder
    public Message(String content, UserChatRoom userChatRoom){
        this.content = content;
        this.userChatRoom = userChatRoom;
        userChatRoom.getMessages().add(this);
    }
}
