package com.springboard.user.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record UserRequest(
    @NotNull(message = "name 값이 필요합니다.")
    String name,

    @Min(value = 0, message = "age 값은 0이상이어야 합니다.")
    Integer age,

    String hobby
) {}