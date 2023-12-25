package com.example.board.exception;

public record ErrorResponse(
        String code,
        String message
) {
}
