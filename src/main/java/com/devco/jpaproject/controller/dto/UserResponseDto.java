package com.devco.jpaproject.controller.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {
    @NotBlank(message = "user name should not be null")
    private String name;

    private int age;

    private String hobby;
}
