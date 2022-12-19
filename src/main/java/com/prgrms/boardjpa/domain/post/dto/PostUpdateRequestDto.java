package com.prgrms.boardjpa.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostUpdateRequestDto {
    private String title;
    private String content;
}
