package com.prgrms.jpa.controller.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostsResponse {

    private final long totalPages;
    private final long totalCount;
    private final List<PostResponse> posts;

    @Builder
    public PostsResponse(long totalPages, long totalCount, List<PostResponse> posts) {
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.posts = new ArrayList<>(posts);
    }
}
