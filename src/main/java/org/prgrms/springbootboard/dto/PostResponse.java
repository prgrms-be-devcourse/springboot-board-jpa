package org.prgrms.springbootboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String createdAt;
    private final String lastModifiedAt;

    private PostResponse() {
        this(null, null, null, null, null);
    }

    @Builder
    public PostResponse(Long id, String title, String content, String createdAt, String lastModifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
