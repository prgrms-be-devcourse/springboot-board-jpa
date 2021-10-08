package org.prgms.board.post.dto;

import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;

    public PostRequest() {
    }
}
