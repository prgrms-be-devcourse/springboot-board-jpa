package com.prgrms.java;

import com.prgrms.java.domain.Post;

public class ModifyPostRequest {

    private final long postId;
    private final String title;
    private final String content;

    public ModifyPostRequest(long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
