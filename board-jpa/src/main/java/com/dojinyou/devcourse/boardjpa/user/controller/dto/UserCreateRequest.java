package com.dojinyou.devcourse.boardjpa.user.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserCreateRequest {

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotNull
    @Positive
    private int age;

    @NotBlank
    @Length(max = 50)
    private String hobby;

    protected UserCreateRequest() {
    }

    public UserCreateRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
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
