package com.programmers.board.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    @Size(min = 1, max = 100, message = "게시글 제목은 1자 이상, 100자 이하여야 합니다.")
    private final String title;

    @Size(min = 1, message = "게시글 본문은 1자 이상이어야 합니다.")
    private final String content;

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
