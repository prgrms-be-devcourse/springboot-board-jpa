package com.board.server.domain.post.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PostsResponse(
        List<PostResponse> postResponses,
        PageInfo pageInfo
) {
    public static PostsResponse of(List<PostResponse> postResponses, PageInfo pageInfo) {
        return PostsResponse.builder()
                .postResponses(postResponses)
                .pageInfo(pageInfo)
                .build();
    }
}
