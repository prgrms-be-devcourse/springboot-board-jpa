package com.will.jpapractice.domain.post.dto;

import com.will.jpapractice.domain.user.dto.UserResponse;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
