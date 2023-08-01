package com.programmers.jpaboard.dto.response;

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
public class PostFindResponse {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private Long userId;

    public static PostFindResponse fromEntity(Post post) {
        return PostFindResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .userId(post.getUser().getId())
                .build();
    }
}
