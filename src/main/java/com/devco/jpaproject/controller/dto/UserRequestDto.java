package com.devco.jpaproject.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "user name should not be null")
    private String name;

    private int age;

    private String hobby;
}
