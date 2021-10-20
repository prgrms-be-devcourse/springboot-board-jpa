package com.example.boardbackend.dto.response;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    private Long id;
    private String title;
    private Long view;
    private String createdBy;

    static public BoardResponse from(PostDto postDto){
        return BoardResponse.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .view(postDto.getView())
                .createdBy(postDto.getUserDto().getName())
                .build();
    }
}
