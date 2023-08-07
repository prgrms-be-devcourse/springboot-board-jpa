package com.example.springbootjpa.ui.dto.user;

import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;

public record UserFindResponse(
        long id,
        String name,
        int age,
        Hobby hobby) {
    public static UserFindResponse from(User user) {
        return new UserFindResponse(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}
