package com.prgrms.jpa.controller.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String name;
    private int age;
    private String hobby;
}
