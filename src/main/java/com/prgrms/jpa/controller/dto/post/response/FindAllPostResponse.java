package com.prgrms.jpa.controller.dto.post.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAllPostResponse {

    private List<GetByIdPostResponse> posts;

    @Builder
    public FindAllPostResponse(List<GetByIdPostResponse> posts) {
        this.posts = posts;
    }
}
