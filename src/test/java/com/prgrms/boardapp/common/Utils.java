package com.prgrms.boardapp.common;

import com.prgrms.boardapp.model.CommonEmbeddable;
import com.prgrms.boardapp.model.User;

public class Utils {

    private Utils() {}

    public static User createUser() {
        return  User.builder()
                .name("user")
                .age(10)
                .build();
    }

    public static User createUserWithName(String name) {
        return  User.builder()
                .name(name)
                .age(10)
                .build();
    }

    public static User createUserWithCreatedBy(String createdBy) {
        return  User.builder()
                .name("name")
                .age(10)
                .commonEmbeddable(CommonEmbeddable
                        .builder()
                        .createdBy(createdBy)
                        .build()
                )
                .build();
    }

    public static User createUser(int age) {
        return  User.builder()
                .name("user")
                .age(age)
                .build();
    }
}
