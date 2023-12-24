package com.example.board.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(

        @NotBlank(message = "제목은 필수 입력값입니다.")
        @Size(min = 3, max = 30, message = "제목은 3 ~ 30자 사이로 입력해 주세요.")
        String title,

        @NotBlank(message = "내용은 필수 입력값입니다.")
        @Size(min = 3, max = 200, message = "내용은 3 ~ 200자 사이로 입력해 주세요.")
        String content
) {
}
