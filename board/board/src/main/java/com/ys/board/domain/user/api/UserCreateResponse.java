package com.ys.board.domain.user.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserCreateResponse {

    private Long userId;

    public UserCreateResponse(Long userId) {
        this.userId = userId;
    }

}
