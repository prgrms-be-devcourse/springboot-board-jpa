package com.programmers.springbootboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {

    private Long id = null;

    private String title;

    private String content;

    private Long userId;

}
