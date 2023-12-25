package com.example.board.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(

        @NotBlank(message = "제목을 입력해주세요.")
        @Size(min = 3, max = 100, message = "제목은 3글자 이상, 최대 100글자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(min = 3, message = "내용은 3글자 이상 입력해주세요.")
        String content
) {
}
