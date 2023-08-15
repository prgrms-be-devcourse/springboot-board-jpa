package com.jpaboard.domain;

import com.jpaboard.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserValidateTest {
    @Test
    void user_name_length_test() {
        StringBuilder name = new StringBuilder();
        for(int i=0; i<10; i++) {
            name.append("이름");
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new User(name.toString(), 10, "movie"));

    }

    @Test
    void user_hobby_length_test() {
        StringBuilder hobby = new StringBuilder();
        for(int i=0; i<10; i++) {
            hobby.append("취미");
        }

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new User("suyeon", 10, hobby.toString()));

    }

    @Test
    void user_age_type_test() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new User("suyeon", -10, "movie"));
    }

    @Test
    void user_name_null_test() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new User(null, 10, "movie"));
    }

    @Test
    void user_hobby_null_test() {
        Assertions.assertThrows(NullPointerException.class,
                () -> new User("suyeon", 10, null));
    }
}
