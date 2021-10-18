package com.programmers.springbootboard.post.dto.request;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;
import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostInsertRequest {
    @ArbitraryAuthenticationPrincipal
    private final String email;
    private final String title;
    private final String content;

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
