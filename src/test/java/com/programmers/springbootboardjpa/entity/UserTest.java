package com.programmers.springbootboardjpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Entity Test")
class UserTest {

    @Test
    void testCreateUserWithBuilder(){
        // Arrange
        String expectedName = "이름";
        int expectedAge = 26;
        String expectedHobby = "취미";
        // Act
        User actualResult = User.builder()
                .name(expectedName)
                .age(expectedAge)
                .hobby(expectedHobby)
                .build();
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getName()).isEqualTo(expectedName);
        assertThat(actualResult.getAge()).isEqualTo(expectedAge);
        assertThat(actualResult.getHobby()).isEqualTo(expectedHobby);
        assertThat(actualResult.getCreatedBy()).isEqualTo(expectedName);
        assertThat(actualResult.getPostList()).isEmpty();
    }

}