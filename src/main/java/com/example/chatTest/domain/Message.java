package com.example.chatTest.domain;

import com.example.chatTest.dto.MessageDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Message extends BaseTimeEntity{

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userChatRoom_id")
    UserChatRoom userChatRoom;

    private String content;

    private MessageType messageType;
}
