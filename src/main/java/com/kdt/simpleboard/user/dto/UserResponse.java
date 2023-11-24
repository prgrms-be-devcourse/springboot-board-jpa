package com.kdt.simpleboard.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    @Builder
    public record CreateUserResponse(
        Long createdId
    )
    {}
}
