package com.example.board.dto.post;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private String title;

    private String content;

    private String name;

    private LocalDateTime createdAt;
}
