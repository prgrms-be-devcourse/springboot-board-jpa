package com.programmers.springbootboardjpa.dto;

import com.programmers.springbootboardjpa.domain.user.Hobby;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private final String name;
    private final Hobby hobby;

    public UserUpdateRequest(String name, Hobby hobby) {
        this.name = name;
        this.hobby = hobby;
    }

}
