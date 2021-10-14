package com.example.kdtboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostRequest {
    private Long postId;
    private String title;
    private String content;
    private Long userId;
}
