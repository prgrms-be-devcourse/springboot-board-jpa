package com.prgrms.java.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

public record UserLoginInfo(@Email @NotBlank String email, @NotBlank String password) {
}
