package com.jpaboard.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @Size(min = 1, max = 50, message = "제목은 1 ~ 50 글자 사이입니다.")
        String title,

        @NotNull
        String content
){
}
