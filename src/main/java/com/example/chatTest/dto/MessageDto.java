package com.example.chatTest.dto;

import com.example.chatTest.domain.Message;
import com.example.chatTest.domain.MessageType;
import com.example.chatTest.domain.UserChatRoom;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;

/**
 * 통신에 사용할 메세지
 * */
@Data
@Transactional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDto {

    private String content;

    private Long roomId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private MessageType type;   //  CHAT, JOIN, LEAVE, PLAYER

    public Message toEntity(UserChatRoom userChatRoom){
        return Message.builder()
                .content(content)
                .messageType(type)
                .userChatRoom(userChatRoom)
                .build();
    }
//
//    @Builder
//    public Message(String content, UserChatRoom userChatRoom, MessageType messageType){
//        this.content = content;
//        this.userChatRoom = userChatRoom;
//        this.messageType = messageType;
//        userChatRoom.getMessages().add(this);
//    }
//

}
