package com.kdt.simpleboard.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    @AllArgsConstructor
    @Getter
    public static class SignUpRes{
        private Long createdId;
    }
}
