package com.programmers.jpaboard.board.controller.dto;

import lombok.Getter;

@Getter
public class BoardUpdateDto {
    private final String title;
    private final String content;

    public BoardUpdateDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
