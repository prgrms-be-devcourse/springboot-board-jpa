package com.programmers.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    private static final String TITLE_VALIDATE = "게시글 제목은 1자 이상, 100자 이하여야 합니다";
    private static final String CONTENT_VALIDATE = "게시글 본문은 1자 이사이어야 합니다";
    private static final String USER_MANDATORY = "사용자 정보는 필수입니다";
    private static final String TITLE_MANDATORY = "게시글 제목은 필수입니다";
    private static final String CONTENT_MANDATORY = "게시글 본문은 필수입니다";

    @NotNull(message = USER_MANDATORY)
    private final Long userId;

    @NotBlank(message = TITLE_MANDATORY)
    @Size(min = 1, max = 100, message = TITLE_VALIDATE)
    private final String title;

    @NotBlank(message = CONTENT_MANDATORY)
    @Size(min = 1, message = CONTENT_VALIDATE)
    private final String content;

    public PostCreateRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
