package com.example.springbootboardjpa.dto.response;

import com.example.springbootboardjpa.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private Long userId;

    @Builder
    private PostResponse(Long postId, String title, String content, Long userId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public static PostResponse fromEntity(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }
}
