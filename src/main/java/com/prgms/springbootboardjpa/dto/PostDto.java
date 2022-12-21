package com.prgms.springbootboardjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String content;
}