package com.assignment.board.dto.post;

import com.assignment.board.dto.user.UserResponseDto;
import lombok.*;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private Long userId;
}
