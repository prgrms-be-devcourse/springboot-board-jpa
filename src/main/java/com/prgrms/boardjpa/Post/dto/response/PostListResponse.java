package com.prgrms.boardjpa.Post.dto.response;

import java.util.List;

public record PostListResponse(List<PostResponse> postResponseList) {

    public static PostListResponse create(List<PostResponse> postResponseList) {
        return new PostListResponse(postResponseList);
    }
}