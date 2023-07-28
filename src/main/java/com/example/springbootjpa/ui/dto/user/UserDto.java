package com.example.springbootjpa.ui.dto.user;

import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;

public record UserDto(
        long id,
        String name,
        int age,
        Hobby hobby) {
    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}
