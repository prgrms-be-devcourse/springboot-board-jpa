package com.kdt.prgrms.board.dto;

import javax.validation.constraints.NotBlank;

public class PostAddRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    public PostAddRequest(String title, String content) {

        this.title = title;
        this.content = content;
    }

    public String getTitle() {

        return title;
    }

    public String getContent() {

        return content;
    }
}
