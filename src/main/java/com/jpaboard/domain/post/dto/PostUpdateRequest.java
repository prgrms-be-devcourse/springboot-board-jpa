package com.jpaboard.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @Size(min = 1, message = "제목은 한 글자 이상입니다.")
        String title,

        @NotNull
        String content
){
}
