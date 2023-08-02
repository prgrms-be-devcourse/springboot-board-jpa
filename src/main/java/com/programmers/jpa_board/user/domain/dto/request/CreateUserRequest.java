package com.programmers.jpa_board.user.domain.dto.request;

public class CreateUserRequest {
    private String name;
    private int age;
    private String hobby;

    public CreateUserRequest() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }
}
