package org.prgms.board.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostRequest {
    @NotBlank
    private Long userId;

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;

    public PostRequest() {
    }

    public PostRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
