package com.example.board.domain.post.dto;

import org.hibernate.validator.constraints.Length;

public record PostUpdateRequest(
        @Length(min = 2, max = 50, message = "게시글 제목은 최소 2자, 최대 50자 입니다.")
        String title,

        @Length(min = 2, max = 200, message = "게시글 내용은 최소 2자, 최대 200자 입니다.")
        String content
) {
}
