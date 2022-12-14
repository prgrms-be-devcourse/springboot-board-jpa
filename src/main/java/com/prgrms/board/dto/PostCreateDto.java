package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreateDto {
    private String title;

    private String content;

    private Long memberId;
}
