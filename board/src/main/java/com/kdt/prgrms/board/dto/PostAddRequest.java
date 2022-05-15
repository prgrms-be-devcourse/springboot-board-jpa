package com.kdt.prgrms.board.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostAddRequest {

    @NotBlank
    @NotNull
    private final String title;

    @NotBlank
    @NotNull
    private final String content;

    @JsonCreator
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
