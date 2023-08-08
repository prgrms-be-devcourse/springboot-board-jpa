package com.blackdog.springbootBoardJpa.domain.user.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserResponse(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotNull
        int age,

        @NotBlank
        String hobby,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime updatedAt
) {
}
