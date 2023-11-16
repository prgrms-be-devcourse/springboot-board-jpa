package com.example.board.dto;

public record PostDto(
        long userId,
        String title,
        String contents
) {
}
