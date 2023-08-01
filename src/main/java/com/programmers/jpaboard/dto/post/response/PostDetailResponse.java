package com.programmers.jpaboard.dto.post.response;

import com.programmers.jpaboard.domain.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponse {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private Long userId;

    public static PostDetailResponse fromEntity(Post post) {
        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .userId(post.getUser().getId())
                .build();
    }
}
