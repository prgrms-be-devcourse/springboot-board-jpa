package com.jpaboard.user.ui.dto;

import lombok.Builder;

public sealed interface UserDto permits Request, Response {
    @Builder
    record Request(String name, int age, String hobby){ }

    @Builder
    record Response(String name, int age, String hobby){ }

}
