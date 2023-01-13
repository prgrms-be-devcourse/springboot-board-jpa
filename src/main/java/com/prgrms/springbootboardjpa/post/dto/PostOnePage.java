package com.prgrms.springbootboardjpa.post.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostOnePage {
    private int allBookCount;
    private int allPageCount;
    private List<PostResponse> pagePosts;

    public PostOnePage() {
    }

    public PostOnePage(int allBookCount, int allPageCount, List<PostResponse> pagePosts) {
        this.allBookCount = allBookCount;
        this.allPageCount = allPageCount;
        this.pagePosts = pagePosts;
    }
}
