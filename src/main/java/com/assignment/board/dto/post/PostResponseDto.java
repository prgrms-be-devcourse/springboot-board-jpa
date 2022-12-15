package com.assignment.board.dto.post;

import com.assignment.board.dto.user.UserResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto userDto;
    private LocalDateTime createdAt;
    private String createdBy;
}
