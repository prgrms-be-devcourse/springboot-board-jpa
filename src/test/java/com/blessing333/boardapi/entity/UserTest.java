package com.blessing333.boardapi.entity;

import com.blessing333.boardapi.entity.exception.UserCreateFailException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {
    private final static String name = "minjae";
    private final static int age = 28;
    @DisplayName("이름, 나이로 User 객체 생성")
    @Test
    void testUserCreate(){
        User user = User.createUser(name, age);

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
    }

    @DisplayName("이름, 나이, 취미로 User 객체 생성")
    @Test
    void testUserCreateWithHobby(){
        String hobby = "독서";

        User user = User.createUserWithHobby(name, age, hobby);

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getAge()).isEqualTo(age);
        assertThat(user.getHobby()).isEqualTo(hobby);
    }

    @DisplayName("User 생성 시 age가 0보다 작거나 같다면 IllegalArgumentException 발생")
    @Test
    void testUserCreateWithAgeUnderZero(){
        Assertions.assertThrows(UserCreateFailException.class,()->User.createUser("minjae",0));
    }
}