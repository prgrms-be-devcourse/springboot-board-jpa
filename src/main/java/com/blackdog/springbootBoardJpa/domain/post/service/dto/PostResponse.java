package com.blackdog.springbootBoardJpa.domain.post.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostResponse(
        @NotNull
        Long id,

        @NotBlank
        String title,

        @NotNull
        @Size(max = 255)
        String content,

        @NotBlank
        String name,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt
) {
}
