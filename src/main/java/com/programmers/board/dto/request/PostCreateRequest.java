package com.programmers.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    @NotNull(message = "사용자 정보는 필수입니다")
    private final Long userId;

    @NotBlank(message = "게시글 제목은 공백일 수 없습니다")
    @Size(min = 1, max = 100, message = "게시글 제목은 1자 이상, 100자 이하여야 합니다")
    private final String title;

    @NotBlank(message = "게시글 본문은 공백일 수 없습니다")
    @Size(min = 1, message = "게시글 본문은 1자 이사이어야 합니다")
    private final String content;

    public PostCreateRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
