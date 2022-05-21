package com.kdt.prgrms.board.dto;

import com.kdt.prgrms.board.entity.post.Post;

public class PostResponse {

    private final long userId;
    private final String userName;
    private final long postId;
    private final String title;
    private final String content;

    public PostResponse(long userId, String userName, long postId, String title, String content) {

        this.userId = userId;
        this.userName = userName;
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public long getUserId() {

        return userId;
    }

    public String getUserName() {

        return userName;
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
