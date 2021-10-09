package org.prgms.board.post.dto;

import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;

    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
