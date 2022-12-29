package com.prgrms.java.dto.user;

import jakarta.validation.constraints.NotNull;

public record PostLoginRequest(@NotNull String email, @NotNull String password) {
}
