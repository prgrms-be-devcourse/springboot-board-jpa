package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "Username text cannot be blank.")
    private String name;
    @Positive(message = "Age cannot be negative or zero.")
    private int age;
    @NotBlank(message = "Hobby text cannot be blank.")
    private String hobby;
}
