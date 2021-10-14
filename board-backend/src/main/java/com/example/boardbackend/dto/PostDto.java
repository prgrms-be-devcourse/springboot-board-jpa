package com.example.boardbackend.dto;

import com.example.boardbackend.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long view;
    private UserDto userDto;
    private LocalDateTime createdAt;
}
