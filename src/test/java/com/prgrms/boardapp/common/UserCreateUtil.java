package com.prgrms.boardapp.common;

import com.prgrms.boardapp.model.User;

public class UserCreateUtil {

    private static Long userId = 1L;

    private UserCreateUtil() {
    }

    public static User createUser() {
        return User.builder()
                .name("user")
                .age(10)
                .build();
    }

    public static User createUserWithId() {
        return User.builder()
                .id(userId++)
                .name("user")
                .age(10)
                .build();
    }

    public static User createUserWithName(String name) {
        return User.builder()
                .name(name)
                .age(10)
                .build();
    }

    public static User createUserWithAge(int age) {
        return User.builder()
                .name("user")
                .age(age)
                .build();
    }
}
