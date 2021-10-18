package com.programmers.springbootboard.post.dto.request;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;

public class PostDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private String email;

    public String getEmail() {
        return email;
    }
}
