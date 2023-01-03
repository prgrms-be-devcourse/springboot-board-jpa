package com.prgrms.java.dto.user;

import com.prgrms.java.domain.HobbyType;
import jakarta.validation.constraints.NotBlank;

public record UserSideInfo(@NotBlank String name, int age, HobbyType hobby) {
}
