package com.prgrms.java.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

public record UserLoginInfo(@Email @NonNull String email, @NotEmpty String password) {
}
