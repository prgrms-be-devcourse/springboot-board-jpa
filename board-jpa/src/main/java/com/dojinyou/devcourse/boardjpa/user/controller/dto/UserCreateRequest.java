package com.dojinyou.devcourse.boardjpa.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserCreateRequest {

    @NotBlank
    @Length(max = 50)
    private final String name;

    @NotNull
    @Positive
    private final int age;

    @NotBlank
    @Length(max = 50)
    private final String hobby;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserCreateRequest(
            @JsonProperty(value = "name") String name,
            @JsonProperty(value = "age") int age,
            @JsonProperty(value = "hobby") String hobby) {
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
