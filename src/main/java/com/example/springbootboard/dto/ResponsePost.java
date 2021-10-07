package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {

    private Long id;
    private String title;
    private String content;

    @Builder
    public ResponsePost(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
