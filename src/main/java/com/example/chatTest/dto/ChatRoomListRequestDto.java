package com.example.chatTest.dto;


import lombok.*;

/**
 *  채팅창 목록 조회시 request dto
 * */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoomListRequestDto {
    private Long userId;

    @Builder
    public ChatRoomListRequestDto(Long userId){
        this.userId = userId;
    }
}
