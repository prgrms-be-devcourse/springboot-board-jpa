package com.prgrms.dto;

import com.prgrms.java.domain.Post;
import org.springframework.util.Assert;

public class CreatePostRequest {

    private final String title;

    private final String content;

    private final PostUserInfo postUserInfo;

    public CreatePostRequest(String title, String content, PostUserInfo postUserInfo) {
        Assert.notNull(title, "title should not null");
        Assert.notNull(content, "content should not null");
        this.title = title;
        this.content = content;
        this.postUserInfo = postUserInfo;
    }

    public Post toEntity() {
        return new Post(this.title, this.content, this.postUserInfo.toEntity());
    }
}
