package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {
    private Long userId;
    private String title;
    private String content;
}
