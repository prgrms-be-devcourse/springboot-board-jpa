package com.prgrms.board.dto.post;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostFindResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostFindResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

}
