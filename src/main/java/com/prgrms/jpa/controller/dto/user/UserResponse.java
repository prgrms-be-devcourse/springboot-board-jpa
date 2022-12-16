package com.prgrms.jpa.controller.dto.user;

import lombok.Builder;

public class UserResponse {

    private final Long id;
    private final String name;
    private final int age;
    private final String hobby;;

    @Builder
    public UserResponse(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
