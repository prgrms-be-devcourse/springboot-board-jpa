package com.example.springbootboard.dto;

import com.example.springbootboard.domain.vo.Name;

public class UserDto {
    Integer age;
    String hobby;
    Name name;

    public UserDto(Integer age, String hobby, Name name) {
        this.age = age;
        this.hobby = hobby;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public Name getName() {
        return name;
    }
}
