package com.programmers.jpaboard.board.controller.dto;

import lombok.Getter;

@Getter
public class BoardCreationDto {
    private final String title;
    private final String content;
    private final Long memberId;

    public BoardCreationDto(String title, String content, Long memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }
}
