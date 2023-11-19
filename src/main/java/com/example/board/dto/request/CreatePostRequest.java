package com.example.board.dto.request;

public record CreatePostRequest(String title, String content, Long authorId) {
}
