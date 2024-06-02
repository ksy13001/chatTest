package com.example.chatTest.dto;

import com.example.chatTest.domain.ChatRoom;
import com.example.chatTest.domain.ChatRoomStatus;
import com.example.chatTest.domain.ChatRoomType;
import lombok.Builder;
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
    private ChatRoomStatus chatRoomStatus;  // "private" | "friends" | "public"
    private ChatRoomType chatRoomType;     // "song" | "album" | "artist" | "playlist"

    private Long songId;
    private Long albumId;
    private Long artistId;
    private Long playlistId;

    @Builder
    public CreateChatRoomRequestDto(Long userId, String name, ChatRoomStatus chatRoomStatus, ChatRoomType chatRoomType,
                                    Long songId, Long albumId, Long artistId, Long playlistId ){
        this.userId = userId;
        this.name = name;
        this.chatRoomStatus = chatRoomStatus;   // PRIVATE, FRIENDS, PUBLIC
        this.chatRoomType = chatRoomType;       //  SONG, ALBUM, ARTIST, PLAYLIST

        this.songId = songId;
        this.albumId = albumId;
        this.artistId = artistId;
        this.playlistId = playlistId;
    }

    public static class CreateChatRoomRequestDtoBuilder {
        public CreateChatRoomRequestDtoBuilder typeId(ChatRoomType chatRoomType, Long typeId) {
            if (chatRoomType == ChatRoomType.SONG) {
                this.songId(typeId);
            } else if (chatRoomType == ChatRoomType.ALBUM) {
                this.albumId(typeId);
            } else if (chatRoomType == ChatRoomType.ARTIST) {
                this.artistId(typeId);
            } else if (chatRoomType == ChatRoomType.PLAYLIST) {
                this.playlistId(typeId);
            }
            return this;
        }
    }

    // ChatRoom 엔티티로 변환
    public ChatRoom toEntity(){
        ChatRoom chatRoom =  ChatRoom.builder()
                            .name(name)
                            .build();
        return chatRoom;
    }

}
