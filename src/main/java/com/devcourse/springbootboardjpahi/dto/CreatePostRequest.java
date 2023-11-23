package com.devcourse.springbootboardjpahi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreatePostRequest(
        @NotBlank(message = "제목은 공백일 수 없습니다.")
        String title,
        @NotNull(message = "내용이 존재하지 않습니다.")
        String content,
        @PositiveOrZero(message = "유효하지 않은 유저 아이디입니다.")
        Long userId
) {
}
