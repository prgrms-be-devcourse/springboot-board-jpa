package com.waterfogsw.springbootboardjpa.user.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record UserAddRequest(
        @NotBlank
        String name,
        @NotBlank @Email
        String email,
        @Min(0)
        int age,
        String hobby
) {
}
