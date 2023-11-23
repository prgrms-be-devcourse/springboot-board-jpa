package com.devcourse.springbootboardjpahi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePostRequest(
        @NotBlank(message = "제목은 공백일 수 없습니다.")
        String title,
        @NotNull(message = "내용이 존재하지 않습니다.")
        String content
) {
}
