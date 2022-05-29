package com.example.board.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public record PostRequestDto (
        @NotNull(message = "Title cannot be null")
        @Max(value = 30, message = "Title should not be greater than 30")
        String title,
        String content,
        UserResponseDto author
){
}
