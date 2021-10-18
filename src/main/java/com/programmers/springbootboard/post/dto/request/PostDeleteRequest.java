package com.programmers.springbootboard.post.dto.request;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;
import com.programmers.springbootboard.annotation.ThreadSafety;
import lombok.Builder;

@ThreadSafety
@Builder
public class PostDeleteRequest {
    @ArbitraryAuthenticationPrincipal
    private final String email;

    public String getEmail() {
        return email;
    }
}
