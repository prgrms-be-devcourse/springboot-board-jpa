package com.juwoong.springbootboardjpa.post.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {

    private Long userId;
    private Long postId;
    private String postTitle;
    private String postContent;

}
