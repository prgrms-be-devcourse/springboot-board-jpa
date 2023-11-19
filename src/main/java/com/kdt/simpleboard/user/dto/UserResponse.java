package com.kdt.simpleboard.user.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    public record SignUpRes(
        Long createdId
    )
    {}
}
