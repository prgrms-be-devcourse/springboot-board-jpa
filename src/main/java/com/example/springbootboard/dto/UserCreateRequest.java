package com.example.springbootboard.dto;

import com.example.springbootboard.entity.Hobby;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank
    @Max(value = 30, message = "max size is 30")
    private String name;

    @NotNull
    @Min(value = 1, message = "age must be higher than 0")
    private int age;

    @NotBlank
    private Hobby hobby;
}
