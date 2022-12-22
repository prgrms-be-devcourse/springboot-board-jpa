package com.prgrms.jpa.controller.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserResponse {

    private long id;

    @Builder
    public CreateUserResponse(long id) {
        this.id = id;
    }
}
