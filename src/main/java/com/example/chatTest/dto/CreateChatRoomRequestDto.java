package com.example.chatTest.dto;

import com.example.chatTest.domain.ChatRoomType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  ChatRoom 생성 request dto
 * */
@Data
@NoArgsConstructor
public class CreateChatRoomRequestDto {
    private Long userId;
    private String name;
    private ChatRoomType chatRoomType;

    private Long songId;
    private Long albumId;
    private Long artistId;
    private Long playlistId;


}
