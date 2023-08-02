package com.jpaboard.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotNull(message = "비회원은 게시글 작성 기능을 이용할 수 없습니다.")
        Long userId,

        @Size(min = 1, max = 50, message = "제목은 1 ~ 50 글자 사이입니다.")
        String title,

        @NotNull
        String content
) {
}
