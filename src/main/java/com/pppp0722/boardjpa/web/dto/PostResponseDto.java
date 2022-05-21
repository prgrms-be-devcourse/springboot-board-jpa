package com.pppp0722.boardjpa.web.dto;

import com.pppp0722.boardjpa.domain.post.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private LocalDateTime createdAt;
    private Long id;
    private String title;
    private String content;
    private UserResponseDto userResponseDto;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
            .createdAt(post.getCreatedAt())
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .userResponseDto(UserResponseDto.from(post.getUser()))
            .build();
    }
}
