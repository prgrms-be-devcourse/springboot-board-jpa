package com.programmers.springbootboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostRequestDto {
    private Long id;
    private String title;
    private String content;
    private String username;
}
