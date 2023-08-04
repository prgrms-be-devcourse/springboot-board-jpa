package com.example.jpaboard.post.controller.dto;

public record SaveApiRequest(Long memberId, String title, String content) {
}
