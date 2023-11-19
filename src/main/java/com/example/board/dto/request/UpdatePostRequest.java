package com.example.board.dto.request;

public record UpdatePostRequest(String title, String content, Long authorId) {
}
