package com.example.board.domain.post.dto;

public record PostRequest(String title, String content, Long memberId) {
}
