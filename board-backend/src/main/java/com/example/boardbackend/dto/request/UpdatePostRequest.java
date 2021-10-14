package com.example.boardbackend.dto.request;

import lombok.Getter;

@Getter
public class UpdatePostRequest {
    private String title;
    private String content;
}
