package com.example.board.Dto;

import com.example.board.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class PostDto {
    private Long postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NonNull
    private UserDto user;
}
