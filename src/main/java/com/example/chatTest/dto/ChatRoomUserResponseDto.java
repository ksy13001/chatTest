package com.example.chatTest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomUserResponseDto {
    String name;

    @Builder
    public ChatRoomUserResponseDto(String name){
        this.name = name;
    }
}
