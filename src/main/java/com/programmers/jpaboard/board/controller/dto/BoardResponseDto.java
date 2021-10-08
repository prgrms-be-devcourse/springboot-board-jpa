package com.programmers.jpaboard.board.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class BoardResponseDto implements Serializable {
    private final String title;
    private final String content;
}
