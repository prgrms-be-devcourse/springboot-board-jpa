package org.prgms.board.post.dto;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CommentRequest {
    @NotNull
    private Long userId;

    @NotBlank(message = "댓글을 입력해주세요")
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
