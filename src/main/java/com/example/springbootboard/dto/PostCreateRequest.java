package com.example.springbootboard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateRequest {
    @NotEmpty
    @Max(value = 30, message = "title is too long, max length = 30")
    private String title;

    @NotEmpty
    @Max(value = 255, message = "content is too long, max length = 255")
    private String content;

    @NotNull
    private Long userId;
}
