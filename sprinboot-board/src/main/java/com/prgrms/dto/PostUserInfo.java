package com.prgrms.dto;

import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.User;



public class PostUserInfo {
    
    private final long id;
    private final String name;
    private final int age;
    private final String hobby;

    public PostUserInfo(long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity() {
        return new User(id, name, age, HobbyType.valueOf(hobby));
    }
}
