package org.prgms.board.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PostRequest {
    @NotNull(message = "제목을 입력해주세요")
    private String title;
    @NotNull(message = "내용을 입력해주세요")
    private String content;

    public PostRequest() {
    }

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
