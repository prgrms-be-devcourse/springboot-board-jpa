package com.prgrms.hyuk.dto;

public class UserDto {

    private String name;
    private int age;
    private String hobby;

    public UserDto(String name, int age, String hobby) {
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
