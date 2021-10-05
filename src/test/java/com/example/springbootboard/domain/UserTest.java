package com.example.springbootboard.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("User 이름은 null을 허용하지 않는다")
    public void testUserNameIsNull() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            User user = User.builder()
                    .createdBy("admin")
                    .createdAt(LocalDateTime.now())
                    .age(1)
                    .hobby(Hobby.SPORTS)
                    .build();

        });
    }

    @Test
    @DisplayName("User의 나이는 1미만이 될 수 없다")
    public void testUserAgeUnder1() throws Exception {
        //given
        int age = 0;

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            User.builder()
                    .createdBy("user")
                    .createdAt(LocalDateTime.now())
                    .name("name")
                    .age(age)
                    .build();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"name.", "name*", "name!", "name&", "name@", "name#",
            "name%","name ","name*","name=","name+"
            })
    @DisplayName("User name은 _ 이외 다른 특수 문자를 허용하지 않는다")
    public void testUserNameSpecial(String name) throws Exception {
        //given

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                    .name(name)
                    .createdBy("createdBy")
                    .createdAt(LocalDateTime.now())
                    .build();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"함승훈", "함seunghun", "ㅎ_seunghun"
    })
    @DisplayName("User name은 영어만 가능하다")
    public void testUserNameKorean(String name) throws Exception {
        //given

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            User.builder()
                    .name(name)
                    .createdBy("createdBy")
                    .createdAt(LocalDateTime.now())
                    .build();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "Name", "nAme", "NAME"})
    @DisplayName("User name은 대소문자를 구분하지 않는다")
    public void testUserNameCase(String inputName) throws Exception {
        //given 
        String lowerCase = "name";

        //when 
        User user = User.builder()
                .createdBy("createdBy")
                .createdAt(LocalDateTime.now())
                .name(inputName)
                .build();
        //then
        assertThat(user.getName()).isEqualTo(lowerCase);
    } 
    
    
}