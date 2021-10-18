package com.prgrms.board.dto.post;

import com.prgrms.board.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostFindResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PostFindResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public static PostFindResponse from(Post post){
        return new PostFindResponse(post);
    }

}
