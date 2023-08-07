package org.prgrms.myboard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.prgrms.myboard.domain.User;

public record UserCreateRequestDto(
    @NotBlank(message = "이름이 비어있습니다.")
    String name,
    @Min(value = 1, message = "나이는 최소 한살입니다.")
    int age,
    String hobby
) {
    public User toUser() {
        return User.builder()
            .name(name)
            .age(age)
            .hobby(hobby)
            .build();
    }
}
