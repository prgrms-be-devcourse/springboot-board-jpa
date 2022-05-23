package com.example.board.dto;

public record PostRequestDto (
        String title,
        String content,
        UserResponseDto author
){
}
