package com.example.board.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public record UserRequestDto (
        @NotNull(message = "Name cannot be null")
        @Max(value = 30, message = "Name should not be greater than 30")
        String name,
        @NotNull(message = "Age cannot be null")
        int age,
        @Max(value = 30, message = "Hobby should not be greater than 30")
        String hobby
){

}
