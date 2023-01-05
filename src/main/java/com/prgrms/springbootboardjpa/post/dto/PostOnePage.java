package com.prgrms.springbootboardjpa.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostOnePage {
    private int allContent;
    private int allPage;
    private List<PostResponse> pagePosts;

    /*
    public class PostResponse {
    private long postId;
    private Member createdBy;
    private String title;
    private String content;
    private LocalDateTime createdAt;
     */

    public PostOnePage() {
    }

    public PostOnePage(int allContent, int allPage, List<PostResponse> pagePosts) {
        this.allContent = allContent;
        this.allPage = allPage;
        this.pagePosts = pagePosts;
    }
}
