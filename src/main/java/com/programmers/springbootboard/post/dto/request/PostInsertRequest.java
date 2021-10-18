package com.programmers.springbootboard.post.dto.request;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;

public class PostInsertRequest {
    @ArbitraryAuthenticationPrincipal
    private String email;
    private String title;
    private String content;

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
