package com.example.chatTest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateChatRoomResponseDto {
    private Long id;
    private String name;

    @Builder
    public CreateChatRoomResponseDto(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
