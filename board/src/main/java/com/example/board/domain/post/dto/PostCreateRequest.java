package com.example.board.domain.post.dto;

public record PostCreateRequest(Long userId, String title, String content) {

}