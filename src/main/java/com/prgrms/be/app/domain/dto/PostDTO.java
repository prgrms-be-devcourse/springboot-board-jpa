package com.prgrms.be.app.domain.dto;

import java.time.LocalDateTime;

public class PostDTO {
    public record CreateRequest(String title, String content, Long userId) {
    }

    public record UpdateRequest(String title, String content, Long postId) {
    }

    public record PostsResponse(String title, Long postId, LocalDateTime createdAt) {
    }

    public record PostDetailResponse(String title, String content, Long postId, LocalDateTime createdAt, Long userId,
                                     String userName) {
    }


}