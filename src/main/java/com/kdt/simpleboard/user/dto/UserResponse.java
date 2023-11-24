package com.kdt.simpleboard.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserResponse {
    @Builder
    public record CreateUserResponse(
        Long createdId
    )
    {}
}
