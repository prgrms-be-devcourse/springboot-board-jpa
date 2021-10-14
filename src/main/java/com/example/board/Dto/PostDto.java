package com.example.board.Dto;

import com.example.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private UserDto user;
}
