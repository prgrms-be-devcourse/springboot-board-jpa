package com.example.board.dto;

public record PostResponseDto (
        Long id,
        String title,
        String content,
        UserResponseDto author
){

}

