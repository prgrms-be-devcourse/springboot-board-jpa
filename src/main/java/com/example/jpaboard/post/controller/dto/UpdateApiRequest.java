package com.example.jpaboard.post.controller.dto;

public record UpdateApiRequest(String title, String content, Long memberId) {
}
