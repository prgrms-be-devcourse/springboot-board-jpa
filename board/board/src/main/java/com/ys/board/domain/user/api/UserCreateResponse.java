package com.ys.board.domain.user.api;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCreateResponse {

    private Long userId;

    public UserCreateResponse(Long userId) {
        this.userId = userId;
    }

}
