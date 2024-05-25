package com.example.chatTest.dto;

import com.example.chatTest.domain.MessageStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;

@Data
@Transactional
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageDto {

    private Long id;

    private String content;

    private Long roomId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;


    @Builder
    public MessageDto(Long id, String content, Long roomId, Long userId){
        this.id = id;
        this.content = content;
        this.roomId = roomId;
        this.userId = userId;
    }
}
