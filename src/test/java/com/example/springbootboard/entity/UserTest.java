package com.example.springbootboard.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class UserTest {

    @Test
    void userArgumentValidationTest() {
        assertThrows(NullPointerException.class, () -> User.builder().build());
        assertThrows(NullPointerException.class, () -> User.builder().age(26).build());
        assertThrows(NullPointerException.class, () -> User.builder().name("용진").build());
    }
}