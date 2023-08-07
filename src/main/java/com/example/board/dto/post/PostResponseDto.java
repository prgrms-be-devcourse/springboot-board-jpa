package com.example.board.dto.post;

import com.example.board.domain.entity.Post;
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

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .name(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
