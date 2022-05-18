package com.prgrms.board.post.dto;

import com.prgrms.board.post.domain.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private String createdBy;

    @QueryProjection
    public PostResponse(Long postId, String title, String content, String createdBy) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .build();
    }
}
