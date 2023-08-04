package com.juwoong.springbootboardjpa.post.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private Long userId;
    private Long postId;
    private String postTitle;
    private String postContent;

    public PostRequest(Long userId, String postTitle, String postContent) {
        this.userId = userId;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

}
