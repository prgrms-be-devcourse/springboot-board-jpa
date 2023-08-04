package dev.jpaboard.user.dto.request;

import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 5) String name,
        @Size(min = 20) String hobby) {
}
