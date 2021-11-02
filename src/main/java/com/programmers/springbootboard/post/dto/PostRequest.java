package com.programmers.springbootboard.post.dto.request;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PostRequest {
    @Getter
    @AllArgsConstructor
    public static class PostInsertRequest {
        @ArbitraryAuthenticationPrincipal
        private String email;
        private String title;
        private String content;
    }
    
    @Getter
    @AllArgsConstructor
    public static class PostUpdateRequest {
        @ArbitraryAuthenticationPrincipal
        private String email;
        private String title;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class PostDeleteRequest {
        @ArbitraryAuthenticationPrincipal
        private Long id;
    }
}
