package com.example.jpaboard.post.service.dto;

public record UpdateRequest(String title, String content, Long memberId) {
}
