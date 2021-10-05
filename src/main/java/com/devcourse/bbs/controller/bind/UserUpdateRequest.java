package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class UserUpdateRequest {
    @NotBlank(message = "Updated username text cannot be blank.")
    private String name;
    @Positive(message = "Updated age cannot be negative or zero.")
    private int age;
    @NotBlank(message = "Updated hobby text cannot be blank.")
    private String hobby;
}
