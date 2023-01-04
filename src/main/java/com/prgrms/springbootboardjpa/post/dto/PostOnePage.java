package com.prgrms.springbootboardjpa.post.dto;

import java.util.List;

public class PostOnePage {
    private int allContent;
    private int allPage;
    private List<PostResponse> pagePosts;

    public PostOnePage(int allContent, int allPage, List<PostResponse> pagePosts) {
        this.allContent = allContent;
        this.allPage = allPage;
        this.pagePosts = pagePosts;
    }
}
