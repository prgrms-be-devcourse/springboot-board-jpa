package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String writer;
}
