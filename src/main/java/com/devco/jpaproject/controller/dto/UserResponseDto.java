package com.devco.jpaproject.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {
    @NotBlank(message = "user name should not be null")
    private String name;

    private int age;

    private String hobby;
}
