package org.prgrms.springbootboard.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    private PostUpdateRequest() {
        this(null, null);
    }

    @Builder
    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
