package com.prgrms.board.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private Long writerId;
}
