package org.programmers.springboardjpa.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserDto {
    public record UserResponse(
            Long id,
            String name,
            int age,
            String hobby
    ) {}

    public record UserRequest(
            @NotBlank(message = "이름은 Blank 일 수 없습니다.")
            String name,

            @Positive(message = "나이는 0보다 많아야 합니다.")
            @NotBlank(message = "나이는 Blank 일 수 없습니다.")
            int age,

            @NotNull
            String hobby
    ) {}
}
