package com.prgrms.java.dto.user;

import com.prgrms.java.domain.User;

public record GetUserDetailsResponse(String name, String email, int age, String hobby) {

    public static GetUserDetailsResponse from(User user) {
        return new GetUserDetailsResponse(user.getName(), user.getEmail(), user.getAge(), user.getHobby().name());
    }
}
