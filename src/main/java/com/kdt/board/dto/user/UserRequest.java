package com.kdt.board.dto.user;

public class UserRequest {
    private String name;

    public UserRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
