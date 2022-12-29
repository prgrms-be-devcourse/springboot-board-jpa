package com.prgrms.java.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UserSideInfo(@NotEmpty String name, int age, @NotEmpty String hobby) {
}
