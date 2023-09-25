package org.prgrms.myboard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.prgrms.myboard.domain.User;

import static org.prgrms.myboard.util.ErrorMessage.MIN_AGE_MESSAGE;
import static org.prgrms.myboard.util.ErrorMessage.NAME_NOT_BLANK_MESSAGE;

public record UserCreateRequestDto(
    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    String name,
    @Min(value = 1, message = MIN_AGE_MESSAGE)
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
