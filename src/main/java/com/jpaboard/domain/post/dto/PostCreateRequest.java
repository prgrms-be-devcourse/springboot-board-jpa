package com.jpaboard.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotNull(message = "비회원은 게시글 작성 기능을 이용할 수 없습니다.")
        Long userId,

        @Size(min = 1, message = "제목은 한 글자 이상입니다.")
        String title,

        @NotNull
        String content
) {
}
