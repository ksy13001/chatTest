package com.example.chatTest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomsDto {
    private String name;

    @Builder
    public ChatRoomsDto(String name){
        this.name = name;
    }
}
