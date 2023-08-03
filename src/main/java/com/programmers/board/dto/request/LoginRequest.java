package com.programmers.board.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LoginRequest {
    private final String name;

    @JsonCreator
    public LoginRequest(String name) {
        this.name = name;
    }
}
