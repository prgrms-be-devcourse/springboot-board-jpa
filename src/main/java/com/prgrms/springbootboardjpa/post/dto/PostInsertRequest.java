package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import com.prgrms.springbootboardjpa.user.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostInsertRequest {
    private Member createdBy;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostInsertRequest() {
    }


    public PostInsertRequest(Member createdBy, String title, String content, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Post toPost() {
        return new Post(this.createdBy, this.title, this.content, this.createdAt);
    }


}
