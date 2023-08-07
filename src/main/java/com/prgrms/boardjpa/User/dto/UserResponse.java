package com.prgrms.boardjpa.User.dto;

import com.prgrms.boardjpa.User.domain.User;

public record UserResponse(Long id, String name, int age, String hobby) {

    public static UserResponse create(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}