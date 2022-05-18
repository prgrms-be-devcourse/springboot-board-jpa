package com.springboard.user.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public record UserRequest(
    @NotNull
    String name,

    @NotNull
    @Digits(integer = 3, fraction = 0)
    Integer age,

    String hobby
) {}