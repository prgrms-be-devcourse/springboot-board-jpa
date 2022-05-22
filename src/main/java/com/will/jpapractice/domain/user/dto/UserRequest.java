package com.will.jpapractice.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Getter
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    @Size(max = 10)
    private String name;

    @NotNull
    @Positive
    private int age;
}
