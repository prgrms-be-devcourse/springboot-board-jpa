package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostInsertRequest {
    private String title;
    private String content;

    public PostInsertRequest() {
    }


    public PostInsertRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toPost() {
        return new Post(this.title, this.content);
    }


}
