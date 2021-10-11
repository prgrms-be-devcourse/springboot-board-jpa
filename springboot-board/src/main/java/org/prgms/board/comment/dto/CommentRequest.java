package org.prgms.board.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequest {
    @NotNull(message = "댓글을 입력해주세요")
    private String content;

    public CommentRequest() {
    }

    public CommentRequest(String content) {
        this.content = content;
    }
}
