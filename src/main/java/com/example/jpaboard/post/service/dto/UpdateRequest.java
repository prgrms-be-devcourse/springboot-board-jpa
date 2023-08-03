package com.example.jpaboard.post.service.dto;

public record UpdateRequest(Long postId, String title, String content, Long memberId) {
}
