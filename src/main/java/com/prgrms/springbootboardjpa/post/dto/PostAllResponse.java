package com.prgrms.springbootboardjpa.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostAllResponse {

    private final List<PostResponse> postResponses;

    public PostAllResponse(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }
}
