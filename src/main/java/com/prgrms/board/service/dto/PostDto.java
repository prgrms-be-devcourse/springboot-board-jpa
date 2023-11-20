package com.prgrms.board.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long id;

    private String title;

    private String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
