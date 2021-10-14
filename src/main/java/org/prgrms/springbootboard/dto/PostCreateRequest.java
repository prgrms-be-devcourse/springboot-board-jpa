package org.prgrms.springbootboard.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    @NotBlank
    private final String title;
    @NotBlank
    private final String content;
    private final Long writerId;

    private PostCreateRequest() {
        this(null, null, null);
    }

    @Builder
    public PostCreateRequest(String title, String content, Long writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }
}
