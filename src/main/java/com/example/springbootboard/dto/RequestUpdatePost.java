package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestUpdatePost {

    private Long id;
    private String title;
    private String content;

    @Builder
    public RequestUpdatePost(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
