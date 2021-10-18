package com.programmers.springbootboard.post.dto.response;

import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostUpdateResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String email;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }
}
