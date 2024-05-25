package com.example.chatTest.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterChatRoomRequestDto {
    private Long userId;
}
