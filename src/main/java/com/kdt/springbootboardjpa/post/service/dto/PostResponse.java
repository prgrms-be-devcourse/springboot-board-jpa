package com.kdt.springbootboardjpa.post.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String memberName;

    @Builder
    public PostResponse(Long id, String title, String content, String memberName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberName = memberName;
    }
}