package com.prgrms.jpa.controller.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FindAllPostResponse {

    private final long totalPages;
    private final long totalCount;
    private final List<GetByIdPostResponse> posts;

    @Builder
    public FindAllPostResponse(long totalPages, long totalCount, List<GetByIdPostResponse> posts) {
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.posts = new ArrayList<>(posts);
    }
}
