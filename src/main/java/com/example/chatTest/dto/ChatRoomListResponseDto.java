package com.example.chatTest.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  채팅창 목록 조회시 response dto
 *  {
 * 		chatRoomId: 1
 * 		chatRoomName: "room1"
 * 		totalMember: 4
 * 		currentMember: 3
 * 		currentSong: "song1"
 * 		lastMessage: "hello"
 * 		lastSentAt: 12:40:49
 * 	    },
 * */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomListResponseDto {

    private Long chatRoomId;
    private String chatRoomName;
    private int totalMember;
    private int currentMember;
    private String currentSong;
    private  String lastMessage;
    private Date lastSentAt;

    @Builder
    public ChatRoomListResponseDto(Long chatRoomId, String chatRoomName, int totalMember,
                                   int currentMember, String currentSong
                                    ,String lastMessage, Date lastSentAt)
    {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.totalMember = totalMember;
        this.currentMember = currentMember;
        this.currentSong = currentSong;
        this.lastMessage = lastMessage;
        this.lastSentAt = lastSentAt;
    }
}
