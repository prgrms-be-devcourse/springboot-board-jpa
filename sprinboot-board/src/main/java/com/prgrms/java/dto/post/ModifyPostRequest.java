package com.prgrms.java.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ModifyPostRequest(@NotBlank String title, @NotBlank String content) {

    public ModifyPostRequest(@JsonProperty("title") String title, @JsonProperty("content") String content) {
        this.title = title;
        this.content = content;
    }
}
